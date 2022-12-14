package com.example.tester;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.tomcat.util.json.JSONParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Test {
    @Data
    static class TestCase {
        int index;
        @JsonProperty("expectedOutPut")
        Long expectedOutPut;
        @JsonProperty("input1")
        Integer input1;
        @JsonProperty("input2")
        Integer input2;
    }

    static class Action {
        public static long tinhDienTichHinhChuNhat(int a, int b) {
           if(a<0 || b <0){
               return -1;
           }
           return a*b;
        }

    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("before class");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("before");
    }

    @org.junit.Test
    public void testOne() throws Exception {
        /*Kiem thử tương đương*/
        String filePath = "src/main/java/com/example/tester/testcase.json";
        processTest(filePath);
    }
    @org.junit.Test
    public void testTWO() throws Exception {
        /*Kiem thử tương đương*/
        String filePath = "src/main/java/com/example/tester/testcase1.json";
        processTest(filePath);
    }



    /*
    test with C2
    20-10-2022
     */
    @org.junit.Test
    public void testWithC2() throws Exception {
        /*Kiem thử tương đương*/
        String filePath = "src/main/java/com/example/tester/doc_20_10_2022/testCaseC2.json";
        processTest(filePath);
    }

    @org.junit.Test
    public void testWithAllCUses() throws Exception {
        /*Kiem thử tương đương*/
        String filePath = "src/main/java/com/example/tester/doc_26_10_2022/testcase26_10_all-c-uses.json";
        processTest(filePath);
    }

    @org.junit.Test
    public void testWithSomePUses() throws Exception {
        /*Kiem thử tương đương*/
        String filePath = "src/main/java/com/example/tester/doc_26_10_2022/testcase26_10_some-p-uses.json";
        processTest(filePath);
    }

    private void assertEqual(int indexOfTestCase,  long expectedOutPut, long outPut) {
        if (expectedOutPut == outPut) {
            System.out.println("INDEX OF TEST CASE " + indexOfTestCase + ": PASS");
        } else {
            System.out.println("INDEX OF TEST CASE " + indexOfTestCase + " NO PASS");
        }

    }

    @After
    public void tearDown() throws Exception {
        System.out.println("after");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("after class");
    }

    private  void processTest(String filePath) throws Exception{
        JSONParser jsonParser = new JSONParser(new FileReader(filePath));
        ArrayList<Object> testCases = jsonParser.parseArray();
        TestCase test;
        ObjectMapper objectMapper = new ObjectMapper();
        int index = 0;
        for (Object x : testCases) {
            test = objectMapper.convertValue(x, TestCase.class);
            assertEqual(index, test.getExpectedOutPut(), Action.tinhDienTichHinhChuNhat(test.getInput1(), test.getInput2()));
            index++;
        }
    }

}
