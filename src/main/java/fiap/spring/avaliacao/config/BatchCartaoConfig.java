package fiap.spring.avaliacao.config;

import fiap.spring.avaliacao.batch.CartaoWriter;
import fiap.spring.avaliacao.model.Cartao;
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
public class BatchCartaoConfig {

    private Logger logger = LoggerFactory.getLogger(BatchCartaoConfig.class);

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public FlatFileItemReader<Cartao> cartoesReader() {
        String fileName = "./files/pending.csv";
        Resource resource = resourceLoader.getResource("file:" + fileName);
        try {
            resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
            return new FlatFileItemReader<Cartao>();
        }
        return new FlatFileItemReaderBuilder<Cartao>()
                .delimited().delimiter(",").names("numero","dataValidade","vencimento","limiteTotal","alunoId")
                .linesToSkip(1)
                .resource(resource)
                .targetType(Cartao.class)
                .name("File Item Reader")
                .build();
    }

    @Bean
    public ItemWriter<Cartao> writerBatchCartoes(){
        return new CartaoWriter();
    }

    @Bean
    @Qualifier("cartoeschunk")
    public Step cartoesChunk(StepBuilderFactory stepBuilderFactory,
                          ItemReader<Cartao> itemReader,
                          CartaoWriter cartaoWriter){
        return stepBuilderFactory.get("Job de processo" + UUID.randomUUID().toString())
                .<Cartao, Cartao>chunk(50)
                .reader(itemReader)
                .writer(cartaoWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean(name = "cartoes")
    public Job jobCartoes(JobBuilderFactory jobBuilderFactory,
                   @Qualifier("cartoeschunk") Step step){
        return jobBuilderFactory.get("Inserir cartoes" + UUID.randomUUID().toString())
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

}
