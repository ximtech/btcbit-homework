package net.btcbit.homework.service;

import net.btcbit.homework.dto.PianoEntry;
import net.btcbit.homework.exception.PianoTransposerException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PianoTransposerService {

    private static final int MIN_OCTAVE = -3;
    private static final int MAX_OCTAVE = 5;
    private static final int NOTES_PER_OCTAVE = 12;
    private static final int MIN_NOTE_NUMBER = 0;
    private static final int MAX_NOTE_NUMBER = 11;

    public PianoEntry transposeNote(PianoEntry entry, int interval) {
        if (entry.octave() < MIN_OCTAVE || entry.octave() > MAX_OCTAVE) {
            throw new PianoTransposerException("Octave number out of range: %s".formatted(entry.octave()));
        }

        if (entry.noteNumber() < MIN_NOTE_NUMBER || entry.noteNumber() > MAX_NOTE_NUMBER) {
            throw new PianoTransposerException("Note number out of range: %s".formatted(entry.noteNumber()));
        }
        int totalSemitones = entry.octave() * NOTES_PER_OCTAVE + entry.noteNumber();    // Convert to total semitones
        int transposedSemitones = totalSemitones + interval;    // Transpose

        // Convert back to octave and note number
        int newOctave = transposedSemitones / NOTES_PER_OCTAVE;
        int newNoteNumber = transposedSemitones % NOTES_PER_OCTAVE;

        if (newNoteNumber < 0) {
            newNoteNumber += NOTES_PER_OCTAVE;
            newOctave -= 1;
        }

        if (newOctave < MIN_OCTAVE || newOctave > MAX_OCTAVE) {
            throw new PianoTransposerException("Transposed note is out of the piano's range");
        }

        return new PianoEntry(newOctave, newNoteNumber);
    }

    public List<PianoEntry> transposePiece(List<PianoEntry> piece, int interval) {
        return Objects.requireNonNull(piece, "Musical piece cannot be null").stream()
                .map((PianoEntry note) -> transposeNote(note, interval))
                .collect(Collectors.toList());
    }
}

