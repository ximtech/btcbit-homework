package net.btcbit.homework;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.activej.inject.annotation.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.btcbit.homework.dto.AppParameters;
import net.btcbit.homework.dto.PianoEntry;
import net.btcbit.homework.exception.PianoTransposerException;
import net.btcbit.homework.service.FileLocationService;
import net.btcbit.homework.service.PianoTransposerService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PianoHandler {
    
    final ObjectMapper objectMapper;
    final FileLocationService fileLocationService;
    final PianoTransposerService pianoTransposerService;
    
    public File executePianoTransposer(AppParameters parameters) throws IOException {
        File currentLocationDir = fileLocationService.getCurrentDir();
        File inputJsonFile = new File(currentLocationDir, parameters.inputJsonFileName());
        log.info("Input json file path: {}", inputJsonFile);
        if (!inputJsonFile.exists()) {
            throw new PianoTransposerException("Input JSON file: [%s] do not exist".formatted(inputJsonFile.getName()));
        }

        List<PianoEntry> inputEntries = objectMapper.readValue(inputJsonFile, new TypeReference<>(){});
        log.info("Total piano entries count to transpose: {}", inputEntries.size());
        List<PianoEntry> transposedEntries = pianoTransposerService.transposePiece(inputEntries, parameters.interval());
        File outputJsonFile = new File(currentLocationDir, parameters.outputJsonFileName());
        log.info("Output json file path: {}", outputJsonFile);
        FileUtils.write(outputJsonFile, objectMapper.writeValueAsString(transposedEntries), Charset.defaultCharset());
        return outputJsonFile;
    }
}
