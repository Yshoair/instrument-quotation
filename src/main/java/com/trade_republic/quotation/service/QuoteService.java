package com.trade_republic.quotation.service;

import com.trade_republic.quotation.data.contract.QuoteData;
import com.trade_republic.quotation.data.entity.QuoteEntity;
import com.trade_republic.quotation.repository.contract.QuoteRepository;
import com.trade_republic.quotation.service.contract.QuoteServiceTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

@Service
public class QuoteService implements QuoteServiceTemplate {

    @Autowired private QuoteRepository quoteRepository;
    private final Logger logger = LogManager.getLogger(QuoteService.class);

    @Override
    public void addQuote(QuoteData quoteData) {
        try {
            QuoteData quoteEntity = new QuoteEntity().map(quoteData);
            quoteRepository.save((QuoteEntity) quoteEntity);
            quoteRepository.flush();
        } catch (InvalidDataAccessApiUsageException e) {
            logger.error("Instrument " + quoteData.getIsin() + " already deleted!");
        }
    }

    @Override
    public void deleteQuotes(String isin) {
        quoteRepository.deleteAllByIsin(isin);
        quoteRepository.flush();
    }
}
