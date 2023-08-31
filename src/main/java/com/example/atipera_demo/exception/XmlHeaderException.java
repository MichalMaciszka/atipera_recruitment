package com.example.atipera_demo.exception;

public class XmlHeaderException extends Exception{
    public XmlHeaderException() {
        super("Header 'Accept' has xml value");
    }
}
