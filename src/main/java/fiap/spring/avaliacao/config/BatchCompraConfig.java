package fiap.spring.avaliacao.config;

import fiap.spring.avaliacao.batch.CompraWriter;
import fiap.spring.avaliacao.model.Compra;
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
public class BatchCompraConfig {

    private Logger logger = LoggerFactory.getLogger(BatchCompraConfig.class);

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public FlatFileItemReader<Compra> comprasReader() {
        String fileName = "./files/pending.csv";
        Resource resource = resourceLoader.getResource("file:" + fileName);
        try {
            resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
            return new FlatFileItemReader<Compra>();
        }
        return new FlatFileItemReaderBuilder<Compra>()
                .delimited().delimiter(",").names("cartaoId","descricao","valor")
                .linesToSkip(1)
                .resource(resource)
                .targetType(Compra.class)
                .name("File Item Reader")
                .build();
    }

    @Bean
    public ItemWriter<Compra> writerBatchCompras(){
        return new CompraWriter();
    }

    @Bean
    @Qualifier("compraschunk")
    public Step comprasChunk(StepBuilderFactory stepBuilderFactory,
                          ItemReader<Compra> itemReader,
                          CompraWriter compraWriter){
        return stepBuilderFactory.get("Job de processo" + UUID.randomUUID().toString())
                .<Compra, Compra>chunk(50)
                .reader(itemReader)
                .writer(compraWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean(name = "compras")
    public Job jobCompras(JobBuilderFactory jobBuilderFactory,
                   @Qualifier("compraschunk") Step step){
        return jobBuilderFactory.get("Inserir compras" + UUID.randomUUID().toString())
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

}
