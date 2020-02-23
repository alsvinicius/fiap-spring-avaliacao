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
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
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
        String fileName = "./files/pending.csv";
        Resource resource = resourceLoader.getResource("file:" + fileName);
        try {
            resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
            return new FlatFileItemReader<Aluno>();
        }
        return new FlatFileItemReaderBuilder<Aluno>()
                .delimited().delimiter(",").names("rm","nome","logradouro","cep","cidade","estado","pais","telefone","possuiCartao")
                .linesToSkip(1)
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
                          ItemWriter<Aluno> itemWriter){
        return stepBuilderFactory.get("Job de processo" + UUID.randomUUID().toString())
                .<Aluno, Aluno>chunk(50)
                .reader(itemReader)
                .writer(itemWriter)
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
