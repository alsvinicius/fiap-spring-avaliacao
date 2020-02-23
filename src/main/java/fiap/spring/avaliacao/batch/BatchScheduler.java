package fiap.spring.avaliacao.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.batch.operations.JobRestartException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static fiap.spring.avaliacao.util.GeneralUtils.deleteFile;
import static fiap.spring.avaliacao.util.GeneralUtils.writeToFile;

@Configuration
@EnableScheduling
public class BatchScheduler {

    private Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    ApplicationContext context;

    @Scheduled(fixedDelay = 5000)
    public void searchFolder() {
        try (Stream<Path> paths = Files.walk(Paths.get("./files"))) {
            lerArquivos(paths.filter(Files::isRegularFile));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Erro ao ler pasta.");
        }
    }

    private void lerArquivos(Stream<Path> files) {
        files.forEach(path -> {
            String fileName = path.getFileName().toString();
            if (!fileName.equals("pending.csv") && fileName.endsWith(".csv")) {
                try {
                    writeToFile(
                            "./files/pending.csv",
                            new String(Files.readAllBytes(Paths.get(path.toString())))
                    );
                    deleteFile(path.toString());
                    runJob(fileName.replace(".csv", ""));
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Erro ao gravar arquivo.");
                }
            }
        });
    }

    private void runJob(String fileName) {
        JobParameters jobParameters = new JobParameters();
        Job jobToRun = (Job) context.getBean(fileName);
        try {
            jobLauncher.run(jobToRun, jobParameters);
        }
        catch (
                JobRestartException
                        | JobExecutionAlreadyRunningException
                        | org.springframework.batch.core.repository.JobRestartException
                        | JobInstanceAlreadyCompleteException
                        | JobParametersInvalidException e
        ) {
            e.printStackTrace();
            logger.error("Erro ao executar job.");
        }
    }
}
