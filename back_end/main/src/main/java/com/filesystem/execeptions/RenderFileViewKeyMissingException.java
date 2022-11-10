package com.filesystem.execeptions;

public class RenderFileViewKeyMissingException extends RuntimeException {

    public RenderFileViewKeyMissingException() {
        super(String.format("when render file online view, file key is missing, cannot create temp file"));
    }

}
