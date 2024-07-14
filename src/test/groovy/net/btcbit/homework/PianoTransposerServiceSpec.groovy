package net.btcbit.homework

import net.btcbit.homework.dto.PianoEntry
import net.btcbit.homework.exception.PianoTransposerException
import net.btcbit.homework.service.PianoTransposerService
import spock.lang.Specification

class PianoTransposerServiceSpec extends Specification {

    def pianoTransposerService = new PianoTransposerService()

    def 'test transposeNote() - should correctly check input values'() {
        when:
        pianoTransposerService.transposeNote(entry, interval)

        then:
        def error = thrown(PianoTransposerException)
        error.message == expectedMessage

        where:
        entry                                                         | interval | expectedMessage
        new PianoEntry(PianoTransposerService.MIN_OCTAVE - 1, 1)      | 3        | "Octave number out of range: ${PianoTransposerService.MIN_OCTAVE - 1}"
        new PianoEntry(PianoTransposerService.MAX_OCTAVE + 1, 1)      | 3        | "Octave number out of range: ${PianoTransposerService.MAX_OCTAVE + 1}"
        new PianoEntry(1, PianoTransposerService.MIN_NOTE_NUMBER - 1) | 3        | "Note number out of range: ${PianoTransposerService.MIN_NOTE_NUMBER - 1}"
        new PianoEntry(1, PianoTransposerService.MAX_NOTE_NUMBER + 1) | 3        | "Note number out of range: ${PianoTransposerService.MAX_NOTE_NUMBER + 1}"
        new PianoEntry(PianoTransposerService.MIN_OCTAVE, 1)          | -3       | "Transposed note is out of the piano's range"
        new PianoEntry(PianoTransposerService.MAX_OCTAVE, 10)         | 3        | "Transposed note is out of the piano's range"
    }
}
