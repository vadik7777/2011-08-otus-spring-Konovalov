package ru.otus.homework4.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("LocalizationIOServiceImpl test")
@ExtendWith(MockitoExtension.class)
class LocalizationIOServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private LocalizationIOServiceImpl localizationIOServiceImpl;

    @DisplayName("shouldCorrectWrite test")
    @Test
    void shouldCorrectWrite() {
        given(localizationService.getMessage("enter_first_name")).willReturn("Enter a name: ");
        localizationIOServiceImpl.write("enter_first_name");
        verify(localizationService, Mockito.times(1)).getMessage("enter_first_name");
        verify(ioService, Mockito.times(1)).write("Enter a name: ");
    }
}