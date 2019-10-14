package me.lemire;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.nio.*;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.stream.*; 
import java.nio.file.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class ParseSpeed {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        
        byte[] json;
        ObjectMapper objectMapper = new ObjectMapper();

        public BenchmarkState() {
            String location = "jsonexamples/twitter.json";
            Path f = FileSystems.getDefault().getPath(location);
            System.out.println("Trying to read "+f); 
            try {
              this.json = Files.readAllBytes(f);
            } catch(Exception e) {
              e.printStackTrace();
            }
            System.out.println("Read "+this.json.length+" bytes"); 
         }

    }

    @Benchmark
    public JsonNode parse(BenchmarkState s) throws IOException {
        return s.objectMapper.readTree(s.json);
    }

}
