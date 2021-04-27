package application;

import java.util.ArrayList;
import java.util.List;
 
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Authorization;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class ADVPayment {
	private static String ID = "ARynkTls2wEPfMI6QcjkjPS3bTlve1QZowbEq5FtP4rLdquKag9XXFtnACm_7itrELv4KQA7RJ2YODnh";
	private static String Secret = "ENhxlStOKQKlxz4Rjy_fbQaGM6_0jzpyhxwF6Vg-Q1LHdOzPwte8BZAWKQXqZiwJD4lY9waSpAySiiD9";
 
	private static String executionMode = "sandbox";
 
	public static void main(String args[]) {
		ADVPayment advp = new ADVPayment();
		advp.ADVPaymentAPI();
	}
	public void ADVPaymentAPI() {
 
		/*
		 * Flow would look like this: 
		 * 1. Create Payer object and set PaymentMethod 
		 * 2. Set RedirectUrls and set cancelURL and returnURL 
		 * 3. Set Details and Add PaymentDetails
		 * 4. Set Amount
		 * 5. Set Transaction
		 * 6. Add Payment Details and set Intent to "authorize"
		 * 7. Create APIContext by passing the clientID, secret and mode
		 * 8. Create Payment object and get paymentID
		 * 9. Set payerID to PaymentExecution object
		 * 10. Execute Payment and get Authorization
		 * 
		 */
 
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
 
		// Redirect URLs
		RedirectUrls advUrls = new RedirectUrls();
		advUrls.setCancelUrl("http://localhost:3000/crunchifyCancel");
		advUrls.setReturnUrl("http://localhost:3000/crunchifyReturn");
 
		// Set Payment Details Object
		Details details = new Details();
		details.setShipping("2.22");
		details.setSubtotal("3.33");
		details.setTax("1.11");
 
		// Set Payment amount
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("6.66");
		amount.setDetails(details);
 
		// Set Transaction information
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("description d payment");
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
 
		// Add Payment details
		Payment payment = new Payment();
		
		// Set Payment intent to authorize
		payment.setIntent("authorize");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		payment.setRedirectUrls(advUrls);
 
		// Pass the clientID, secret and mode. The easiest, and most widely used option.
		APIContext apiContext = new APIContext(ID, Secret, executionMode);
 
		try {
 
			Payment myPayment = payment.create(apiContext);
 
			System.out.println("createdPayment Obejct Details ==> " + myPayment.toString());
 
			// Identifier of the payment resource created 
			payment.setId(myPayment.getId());
 
			PaymentExecution paymentExecution = new PaymentExecution();
 
			// Set your PayerID. The ID of the Payer, passed in the `return_url` by PayPal.
			paymentExecution.setPayerId("55L9YJPUBNYFS");
 
			// This call will fail as user has to access Payment on UI. Programmatically
			// there is no way you can get Payer's consent.
			Payment authPayment = payment.execute(apiContext, paymentExecution);
 
			// Transactional details including the amount and item details.
			Authorization authorization = authPayment.getTransactions().get(0).getRelatedResources().get(0).getAuthorization();
 
			log("Here is your Authorization ID" + authorization.getId());
 
		} catch (PayPalRESTException e) {
			System.err.println(e.getDetails());
		}
	} 
 
	private void log(String string) {
		System.out.println(string);
 
	}
}
