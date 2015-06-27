package com.wantedtech.common.xpresso.types;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Iterator;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.exceptions.RuntimeIOException;

public class HappyFile implements Iterable<String>,Iterator<String>, Serializable, AutoCloseable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8081771032161336504L;
	
	File file;
	Writer outWriter;
	BufferedReader bufferedReader;
	FileInputStream fileInputStream;
	OutputStream fileOutputStream;
	
	String lastReadLine;
	
	String charset;
	String operation;
	String path;
	
	public HappyFile(String path,String operation,String encoding) throws RuntimeIOException,UnsupportedOperationException {
		this.path = path;
		try {
			file = new java.io.File(path);
		} catch(Exception e) {
			throw new RuntimeIOException("Problem opening file: " + path);
		}
		switch(encoding.toLowerCase()){
			case "utf-8":
				charset = "UTF-8";
				break;
			case "utf8":
				charset = "UTF-8";
				break;
			case "latin-1":
				charset = "ISO-8859-1";
				break;
			case "latin1":
				charset = "ISO-8859-1";
				break;
			default:
				charset = "UTF-8";
		}
		this.operation = operation.toLowerCase();
		switch(operation){
			case "r":
				try {
					this.fileInputStream = new FileInputStream(file);
			        this.bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream,this.charset));
				} catch (Exception e) {
					throw new RuntimeIOException(e.getMessage());
				}
				break;
			case "w":
				try {
					this.fileOutputStream = new FileOutputStream(file);
					this.outWriter = new BufferedWriter(new OutputStreamWriter(this.fileOutputStream, charset));
				} catch (Exception e) {
					throw new RuntimeIOException(e.getMessage());
				}
				break;
			case "a":
				try {
					outWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path,true), charset));
				} catch (Exception e) {
					throw new RuntimeIOException(e.getMessage());
				}
				break;
			case "rb":
				try {
					this.fileInputStream = new FileInputStream(file);
				} catch (Exception e) {
					throw new RuntimeIOException(e.getMessage());
				}
				break;
			case "wb":
				try {
					this.fileOutputStream = new FileOutputStream(file);
				} catch (Exception e) {
					throw new RuntimeIOException(e.getMessage());
				}
				break;
			default:
				throw new UnsupportedOperationException("Unsupported operation: " + operation);
		}

	}
	
	public HappyFile(String path,String operation) throws RuntimeIOException {
		this(path,operation,"UTF-8");
	}
	
	// The next three methods implement Iterator.
	public boolean hasNext() {
		if(!operation.equals("r")){
			throw new UnsupportedOperationException();
		}
		try {
			lastReadLine = bufferedReader.readLine();
			if(lastReadLine != null){
				return true;
			}else{
				bufferedReader.close();				
			}
		} catch (IOException e) {
			return false;
		}
		return false;
	}

	public String next() {
		if(!operation.equals("r")){
			throw new UnsupportedOperationException();
		}
		return lastReadLine;
	}
	
	public void reset() {
		if(!operation.equals("r")){
			throw new UnsupportedOperationException();
		}
		try {
			this.fileInputStream = new FileInputStream(file);
		    try {
				this.bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream,this.charset));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// This method implements Iterable.
	public Iterator<String> iterator() {
		reset();
		return this;
	}
	
	public void writeLine(String line) throws RuntimeIOException {
		if(!operation.equals("w")){
			throw new UnsupportedOperationException();
		}

		try {
			this.outWriter.write(line+"\n");
		} catch (Exception e) {
			throw new RuntimeIOException(e.getMessage());
		}

	}
	
	public void write(String string) throws RuntimeIOException {
		if(!operation.equals("w")){
			throw new UnsupportedOperationException();
		}
		try {
			this.outWriter.write(string);
		} catch (Exception e) {
			throw new RuntimeIOException(e.getMessage());
		}
	}
	
	public void write(byte[] byteArray) throws RuntimeIOException {
		if(!operation.equals("wb")){
			throw new UnsupportedOperationException();
		}
		try {
			this.fileOutputStream.write(byteArray);
		} catch (Exception e) {
			throw new RuntimeIOException(e.getMessage());
		}
	}
	
	public byte[] read() throws RuntimeIOException {
		if(!operation.equals("rb")){
			throw new UnsupportedOperationException();
		}
		byte[] fileData = new byte[(int)this.file.length()];
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(fileData);
			dis.close();
			return fileData;
		} catch (Exception e) {
			throw new RuntimeIOException(e.getMessage());
		}
	}
	
	public byte[] read(int count) throws RuntimeIOException {
		if(!operation.equals("rb")){
			throw new UnsupportedOperationException();
		}
		byte[] fileData = new byte[count];
		try {
			if(fileInputStream.read(fileData) != -1) {
				return fileData;
			}else{
				return new byte[0];
			}
		} catch (Exception e) {
			throw new RuntimeIOException(e.getMessage());
		}
	}
	
	public InputStream getInputStream() {
		return this.fileInputStream;
	}
	
	public OutputStream getOutputStream() {
		return this.fileOutputStream;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void close() {
		try{
			outWriter.close();
		}catch(Exception e){
			
		}
		try{
			bufferedReader.close();
		}catch(Exception e){
	
		}
		try{
			fileInputStream.close();
		}catch(Exception e){
	
		}
		try{
			fileOutputStream.close();
		}catch(Exception e){
	
		}
	}
	
	@Override
	public String toString() {
		return x.String("").join(x.list(this));
	}
	
	@Override
	protected void finalize() {
		try {
		    close();
		} catch (Exception e) {

		}
	}
}