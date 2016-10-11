package cn.blueshit.web.test.mvctest;

import org.junit.Test;
import org.mockito.InOrder;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


/**
 * Created by zhaoheng on 16/10/5.
 */
public class MockitoTest {

    @Test
    public void test1() {

        List mockList = mock(List.class);
        mockList.add("one");
        mockList.clear();
        mockList.add("3"); // no verify? OK
        verify(mockList).add("one");
        verify(mockList).clear();
        verify(mockList).add("2");
    }

    @Test
    public void test2() {
        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException("out of bound"));
        // following prints "first"git
        System.out.println(mockedList.get(0));
        // following throws runtime exception
        //System.out.println(mockedList.get(1));
        // following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
        //最少几次调用 get(0)方法
        verify(mockedList, atLeast(1)).get(0);
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(0)).thenReturn("oops");
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(0));
    }

    @Test
    public void test3() {
        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(anyInt())).thenReturn("testelement");
        System.out.println(mockedList.get(99));
        verify(mockedList).get(anyInt());

    }

    /**
     * 验证方法调用次数
     */
    @Test
    public void test4() {
        LinkedList mockedList = mock(LinkedList.class);
        mockedList.add("once");
        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");
        //验证mocklist调用了 add("once")
        verify(mockedList).add("once");
        //验证调用了一次 add("once")
        verify(mockedList, times(1)).add("once");

        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        verify(mockedList, never()).add("never happened");

        verify(mockedList, atLeastOnce()).add("three times");
        //验证最少调用了2次 add(five times)
        //verify(mockedList, atLeast(2)).add("five times");
        //验证最多调用了5次 add("three times")
        // verify(mockedList, atMost(5)).add("three times");
    }


    /**
     * 执行顺序验证
     */
    @Test
    public void test5() {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        //following will make sure that add is first called with "was added first, then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        InOrder inOrder1 = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder1.verify(firstMock).add("was called first");
        inOrder1.verify(secondMock).add("was called second");
        // Oh, and A + B can be mixed together at will
    }

}
