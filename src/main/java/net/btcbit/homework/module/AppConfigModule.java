package net.btcbit.homework.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import net.btcbit.homework.service.FileLocationService;
import net.btcbit.homework.service.PianoTransposerService;

public class AppConfigModule extends AbstractModule {
    
    @Provides
    public FileLocationService fileLocationUtil() {
        return new FileLocationService();
    }
    
    @Provides
    public PianoTransposerService pianoTransposer() {
        return new PianoTransposerService();
    }
    
    @Provides
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
