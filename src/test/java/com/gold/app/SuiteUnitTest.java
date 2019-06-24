package com.gold.app;

import com.gold.service.impl.AuthorServiceImplTest;
import com.gold.service.impl.BookContentServiceImplTest;
import com.gold.service.impl.BookServiceImplTest;
import com.gold.service.impl.GenreServiceImplTest;
import com.gold.service.impl.PublisherServiceImplTest;
import com.gold.service.impl.UserServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthorServiceImplTest.class,
        BookContentServiceImplTest.class,
        BookServiceImplTest.class,
        GenreServiceImplTest.class,
        PublisherServiceImplTest.class,
        UserServiceImplTest.class})
public class SuiteUnitTest {
}
