package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework14.domain.*;
import ru.otus.homework14.service.AuthorService;
import ru.otus.homework14.service.BookService;
import ru.otus.homework14.service.CommentService;
import ru.otus.homework14.service.GenreService;

import javax.persistence.*;

@RequiredArgsConstructor
@Configuration
public class JobConfig {

    private static final int CHUNK_SIZE = 5;
    public static final String MIGRATION_JOB_NAME = "migrationJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;

    @Bean
    public Job migrationJob(Step transformAuthorStep, Step transformGenreStep,
                            Step transformBookStep, Step transformCommentStep) {
        return jobBuilderFactory.get(MIGRATION_JOB_NAME)
                .start(transformAuthorStep)
                .next(transformGenreStep)
                .next(transformBookStep)
                .next(transformCommentStep)
                .build();
    }

    @Bean
    public Step transformAuthorStep(ItemReader<AuthorJpa> readerAuthor,
                                    ItemProcessor<AuthorJpa, AuthorMongo> processorAuthor,
                                    ItemWriter<AuthorMongo> writerAuthor) {
        return stepBuilderFactory.get("transformAuthorStep")
                .<AuthorJpa, AuthorMongo>chunk(CHUNK_SIZE)
                .reader(readerAuthor)
                .processor(processorAuthor)
                .writer(writerAuthor)
                .build();
    }

    @Bean
    public Step transformGenreStep(ItemReader<GenreJpa> readerGenre,
                                   ItemProcessor<GenreJpa, GenreMongo> processorGenre,
                                   ItemWriter<GenreMongo> writerGenre) {
        return stepBuilderFactory.get("transformGenreStep")
                .<GenreJpa, GenreMongo>chunk(CHUNK_SIZE)
                .reader(readerGenre)
                .processor(processorGenre)
                .writer(writerGenre)
                .build();
    }

    @Bean
    public Step transformBookStep(ItemReader<BookJpa> readerBook,
                                  ItemProcessor<BookJpa, BookMongo> processorBook,
                                  ItemWriter<BookMongo> writerBook) {
        return stepBuilderFactory.get("transformBookStep")
                .<BookJpa, BookMongo>chunk(CHUNK_SIZE)
                .reader(readerBook)
                .processor(processorBook)
                .writer(writerBook)
                .build();
    }

    @Bean
    public Step transformCommentStep(ItemReader<CommentJpa> readerComment,
                                     ItemProcessor<CommentJpa, CommentMongo> processorComment,
                                     ItemWriter<CommentMongo> writerComment) {
        return stepBuilderFactory.get("transformCommentStep")
                .<CommentJpa, CommentMongo>chunk(CHUNK_SIZE)
                .reader(readerComment)
                .processor(processorComment)
                .writer(writerComment)
                .build();
    }

    @Bean(destroyMethod = "")
    public JpaPagingItemReader<AuthorJpa> readerAuthor(EntityManagerFactory entityManagerFactory) {
        var reader = new JpaPagingItemReader<AuthorJpa>();
        reader.setQueryString("select a from AuthorJpa a");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @Bean(destroyMethod = "")
    public JpaPagingItemReader<GenreJpa> readerGenre(EntityManagerFactory entityManagerFactory) {
        var reader = new JpaPagingItemReader<GenreJpa>();
        reader.setQueryString("select g from GenreJpa g");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @Bean(destroyMethod = "")
    public JpaPagingItemReader<BookJpa> readerBook(EntityManagerFactory entityManagerFactory) {
        var reader = new JpaPagingItemReader<BookJpa>();
        reader.setQueryString("select b from BookJpa b join fetch b.author join fetch b.genre");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @Bean(destroyMethod = "")
    public JpaPagingItemReader<CommentJpa> readerComment(EntityManagerFactory entityManagerFactory) {
        var reader = new JpaPagingItemReader<CommentJpa>();
        reader.setQueryString("select c from CommentJpa c join fetch c.book b join fetch b.author join fetch b.genre");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(CHUNK_SIZE);
        return reader;
    }

    @Bean
    public MongoItemWriter<AuthorMongo> writerAuthor(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<AuthorMongo>()
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<GenreMongo> writerGenre(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<GenreMongo>()
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<BookMongo> writerBook(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<BookMongo>()
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<CommentMongo> writerComment(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<CommentMongo>()
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public ItemProcessor<AuthorJpa, AuthorMongo> processorAuthor() {
        return authorService::convert;
    }

    @Bean
    public ItemProcessor<GenreJpa, GenreMongo> processorGenre() {
        return genreService::convert;
    }

    @Bean
    public ItemProcessor<BookJpa, BookMongo> processorBook() {
        return bookService::convert;
    }

    @Bean
    public ItemProcessor<CommentJpa, CommentMongo> processorComment() {
        return commentService::convert;
    }
}