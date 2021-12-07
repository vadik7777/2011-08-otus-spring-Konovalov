package ru.otus.homework13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework13.domain.Book;
import ru.otus.homework13.exception.NotFoundException;
import ru.otus.homework13.mapper.BookMapper;
import ru.otus.homework13.repository.BookRepository;
import ru.otus.homework13.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final MutableAclService mutableAclService;

    @Transactional
    @Override
    public BookDto create(BookDto bookDto) {
        bookDto =  bookMapper.toDto(bookRepository.save(bookMapper.toEntity(bookDto)));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectIdentity oid = new ObjectIdentityImpl(bookDto);

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setEntriesInheriting(false);
        authentication.getAuthorities().forEach(authority -> {
            acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, new GrantedAuthoritySid(authority), true );
        });
        mutableAclService.updateAcl(acl);

        return bookDto;
    }

    @PreAuthorize("hasPermission(#bookDto, 'ADMINISTRATION')")
    @Transactional
    @Override
    public Optional<BookDto> update(long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(book -> {
                    Book updateBook = bookMapper.toEntity(bookDto);
                    BeanUtils.copyProperties(updateBook, book);
                    return bookMapper.toDto(book);
                });
    }

    @PostAuthorize("returnObject.empty || hasPermission(returnObject.get(), 'ADMINISTRATION')")
    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    @PostFilter("hasPermission(filterObject, 'ADMINISTRATION')")
    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasPermission(#bookDto, 'ADMINISTRATION')")
    @Transactional
    @Override
    public void delete(BookDto bookDto) {
        try {
            bookRepository.deleteById(bookDto.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }
}