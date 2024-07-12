package net.btcbit.homework;

import io.activej.inject.Injector;
import io.activej.inject.module.ModuleBuilder;
import lombok.extern.slf4j.Slf4j;
import net.btcbit.homework.dto.AppParameters;
import net.btcbit.homework.exception.PianoTransposerException;
import net.btcbit.homework.module.AppConfigModule;

import java.util.Arrays;

@Slf4j
public class PianoTransposerApplication {

    public static final int REQUIRED_ARGS_COUNT = 3;

    public static void main(String[] args) {
        log.info("Application started with params: {}", Arrays.toString(args));
        try {
            AppParameters parameters = convertArgs(args);
            Injector.of(new AppConfigModule(),
                    ModuleBuilder.create()
                            .bind(PianoHandler.class)
                            .build())
                    .getInstance(PianoHandler.class)
                    .executePianoTransposer(parameters);
            log.info("Application successfully finished");
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
    }
    
    private static AppParameters convertArgs(String[] args) {
        if (args == null || args.length < REQUIRED_ARGS_COUNT) {
            throw new PianoTransposerException("Invalid args provided. Should be inputFile, semitone, outputFile. Example command: [java -jar task.jar in/a.json 5 out/b.json]");
        }
        return new AppParameters(args[0], Integer.parseInt(args[1]), args[2]);
    }
}
