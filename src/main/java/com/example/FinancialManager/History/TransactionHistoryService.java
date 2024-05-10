package com.example.FinancialManager.History;


import com.example.FinancialManager.database.Repositories.TransactionHistoryRepository;
import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.userHistory.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
@Setter
public class TransactionHistoryService {
    TransactionHistoryRepository transactionHistoryRepository;
    TransactionHistoryConverters transactionHistoryConverters;
    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryService.class);
    public List<TransactionHistoryResponse> getTransactionsList(){
        List<TransactionHistory> transactionHistoryList = new ArrayList<>();
        List<TransactionHistoryResponse> transactionResponseList = new ArrayList<>();
        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transactionHistoryList = transactionHistoryRepository.findAllByUserData(userData).orElseThrow(() -> new RuntimeException("Transaction history not found"));
        for(TransactionHistory record : transactionHistoryList){
            transactionResponseList.add(transactionHistoryConverters.convertToTransactionObject(record));

        }
        return transactionResponseList;
    }


}
