package net.btcbit.homework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public record PianoEntry(
        @JsonProperty int octave,
        @JsonProperty int noteNumber) {

}
