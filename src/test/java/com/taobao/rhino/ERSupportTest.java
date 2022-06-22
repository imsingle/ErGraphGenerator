package com.taobao.rhino;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple ERSupport.
 */
public class ERSupportTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer("abcdefg");
        StringBuilder stringBuilder = new StringBuilder(stringBuffer);
        //System.out.println(stringBuffer.delete(1, 2));
        System.out.println(stringBuilder.delete(1, 2));

        //String json = "{\"name\":\"school1\", 'position':{'name':'china', 'price':{'num':1000}}}";
        //School school = JSON.parseObject(json, School.class, Feature.SupportNonPublicField);
        //System.out.println(school);
    }

    static class School {
        private String name;
        private Position position;

        public School() {

        }

        public School(String xx) {

        }
    }

    static class Position {
        private String name;
        private Price price;
    }

    static class Price {
        private Long num;
    }
}
