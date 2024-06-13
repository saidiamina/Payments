import com.example.payments.model.Transaction;
import com.example.payments.model.Item;
import com.example.payments.service.TransactionService;
import com.example.payments.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() {
        // Given
        double pricePerItem = 19.99;
        int quantity = 5;
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            items.add(new Item("T-shirt", pricePerItem, 1));
        }

        Transaction transaction = new Transaction(99.95, Transaction.PaymentType.CREDIT_CARD, items);

        // When
        transactionService.createTransaction(transaction);

        // Then
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).save(transactionCaptor.capture());
        Transaction capturedTransaction = transactionCaptor.getValue();

        assertEquals(Transaction.Status.NEW, capturedTransaction.getStatus());
        assertEquals(99.95, capturedTransaction.getAmount());
        assertEquals(Transaction.PaymentType.CREDIT_CARD, capturedTransaction.getPaymentType());
        assertEquals(5, capturedTransaction.getItems().size());
        assertEquals("T-shirt", capturedTransaction.getItems().get(0).getName());
        assertEquals(pricePerItem, capturedTransaction.getItems().get(0).getPrice());
        assertEquals(1, capturedTransaction.getItems().get(0).getQuantity());
    }
    @Test
    public void testUpdateTransactionStatusAuthorized() {
        // Given
        Long transactionId = 1L;
        Transaction transaction = new Transaction(99.95, Transaction.PaymentType.CREDIT_CARD, new ArrayList<>());
        transaction.setId(transactionId);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // When
        Transaction updatedTransaction = transactionService.updateTransactionStatus(transactionId, Transaction.Status.AUTHORISED);

        // Then
        assertEquals(Transaction.Status.AUTHORISED, updatedTransaction.getStatus());
    }

}
