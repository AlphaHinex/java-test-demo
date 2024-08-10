package com.example.demo;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class DemoApplicationTests {

	private static Logger LOGGER = LoggerFactory.getLogger(DemoApplicationTests.class);

	@BeforeAll
	static void setup() {
		LOGGER.info("@BeforeAll - executes once before all test methods in this class");
	}

	@BeforeEach
	void init() {
		LOGGER.info("@BeforeEach - executes before each test method in this class");
	}

	@AfterEach
	void tearDown() {
		LOGGER.info("@AfterEach - executed after each test method.");
	}

	@AfterAll
	static void done() {
		LOGGER.info("@AfterAll - executed after all test methods.");
	}

	@Test
	void contextLoads() {
	}

	@DisplayName("Single test successful")
	@Test
	void testSingleSuccessTest() {
		LOGGER.info("Success");
	}

	@Test
	@Disabled("Not implemented yet")
	void testShowSomething() {
	}

	@Test
	void groupAssertions() {
		int[] numbers = {0, 1, 2, 3, 4};
		assertNotNull(numbers);
		assertAll("numbers",
			() -> assertEquals(0, numbers[0]),
			() -> assertSame(3, numbers[3]),
			() -> assertArrayEquals(new int[]{0, 1, 2, 3, 4}, numbers)
		);
	}

	@Test
	void testOnlyOnCiServer() {
		assumeFalse("CI".equals(System.getenv("ENV")));
		// remainder of test
	}

	@Test
	void shouldThrowException() {
		Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
			throw new UnsupportedOperationException("Not supported");
		});
		assertEquals("Not supported", exception.getMessage());
	}

	@Test
	void assertThrowsException() {
		String str = null;
		assertThrows(IllegalArgumentException.class, () -> {
			Integer.valueOf(str);
		});
	}

	@Test
	void mockAndVerify() {
		List<String> mockedList = mock(List.class);

		mockedList.add("one");
		mockedList.add("two");
		mockedList.add("two");
		mockedList.add("three");
		verify(mockedList).add("three");

		verify(mockedList, times(2)).add("two");
		verify(mockedList, atLeastOnce()).add("three");
		verify(mockedList, atMost(3)).add("one");
	}

	@Test
	void spyAndStub() {
		List<String> list = new ArrayList<>();
		List<String> spiedList = spy(list);
		spiedList.add("one");
		spiedList.add("two");
		spiedList.add("three");
		assertEquals(3, spiedList.size());

		when(spiedList.get(0)).thenReturn("first");
		assertEquals("first", spiedList.get(0));
		assertEquals("two", spiedList.get(1));
	}

}
