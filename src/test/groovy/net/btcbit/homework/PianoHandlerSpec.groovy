package net.btcbit.homework

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import net.btcbit.homework.dto.AppParameters
import net.btcbit.homework.dto.PianoEntry
import net.btcbit.homework.service.FileLocationService
import net.btcbit.homework.service.PianoTransposerService
import org.apache.commons.io.FileUtils
import spock.lang.Specification

class PianoHandlerSpec extends Specification {

    FileLocationService fileLocationService = Spy {
        getCurrentDir() >> new File("src/test/resources")
    }

    PianoTransposerService pianoTransposerService = Spy()
    ObjectMapper objectMapper = new ObjectMapper()
    
    PianoHandler pianoHandler = new PianoHandler(objectMapper, fileLocationService, pianoTransposerService)

    def 'test executePianoTransposer() - happy path'() {
        given:
        def testOutJsonFile = new File("src/test/resources/out_test.json")
        def appParams = new AppParameters("in.json", -3, "out.json")
        
        when:
        def outputJsonFile = pianoHandler.executePianoTransposer(appParams)
        
        then:
        def inputList = objectMapper.readValue(outputJsonFile, new TypeReference<List<PianoEntry>>(){})
        def outputList = objectMapper.readValue(testOutJsonFile, new TypeReference<List<PianoEntry>>(){})
        inputList.size() == 82
        inputList == outputList
        
        cleanup:
        FileUtils.forceDelete(outputJsonFile)
    }
}
