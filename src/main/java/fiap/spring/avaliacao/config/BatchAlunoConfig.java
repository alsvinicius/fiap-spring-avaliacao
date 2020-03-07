package fiap.spring.avaliacao.config;

import fiap.spring.avaliacao.batch.AlunoWriter;
import fiap.spring.avaliacao.model.Aluno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.UUID;

@Configuration
@EnableBatchProcessing
public class BatchAlunoConfig {

    private Logger logger = LoggerFactory.getLogger(BatchAlunoConfig.class);

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public FlatFileItemReader<Aluno> itemReader() {
        String fileName = "./files/pending.txt";
        Resource resource = resourceLoader.getResource("file:" + fileName);
        try {
            resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
            return new FlatFileItemReader<Aluno>();
        }
        return new FlatFileItemReaderBuilder<Aluno>()
                .fixedLength()
                .columns(new Range(1, 40), new Range(42, 49), new Range(50, 55))
                .names("nome", "rm", "numeroCadastro")
                .resource(resource)
                .targetType(Aluno.class)
                .name("File Item Reader")
                .build();
    }

    @Bean
    public ItemWriter<Aluno> itemWriter(){
        return new AlunoWriter();
    }

    @Bean
    @Qualifier("stepchunk")
    public Step stepChunk(StepBuilderFactory stepBuilderFactory,
                          ItemReader<Aluno> itemReader,
                          ItemWriter<Aluno>     itemWriter){
        return stepBuilderFactory.get("Job de processo" + UUID.randomUUID().toString())
                .<Aluno, Aluno>chunk(100000)
                .reader(itemReader)
                .writer(itemWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy((throwable, i) -> {

                    if(throwable instanceof FlatFileParseException) {
                        FlatFileParseException exception = (FlatFileParseException) throwable;
                        String input = exception.getInput();
                        if (input.length() == 0 || !input.substring(0,1).matches("/([A-Za-z])/g")) {
                            return true;
                        }
                    }

                    if(throwable instanceof IncorrectTokenCountException) {
                        logger.error("Linha com menos tokens do que o necessário");
                        return true;
                    }

                    return false;
                })
                .allowStartIfComplete(true)
                .build();
    }

    @Bean(name = "alunos")
    public Job job(JobBuilderFactory jobBuilderFactory,
                   @Qualifier("stepchunk") Step step){
        return jobBuilderFactory.get("Inserir alunos" + UUID.randomUUID().toString())
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

}
