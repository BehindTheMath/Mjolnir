package uk.org.sith.mjolnir;

import java.io.IOException;

/**
 * http://www.nvenky.in/2010/01/crack-pdf-password-brute-force.html
 * 
 * @author solo
 * 
 */
public class Test {

	private static final String PDF_FILE_PATH = "c:/file2.pdf";
	private static final int PASSWORD_MAX_LENGTH = 4;
	private static final int PASSWORD_MIN_LENGTH = 2;
	// Array with characters to use in Brute Force Algorithm.
	// You can remove or add more characters in this array.
	private static char fCharList[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		PDDocument document = null;
		boolean found = false;
		try {
			document = PDDocument.load(PDF_FILE_PATH);

			for (int i = PASSWORD_MIN_LENGTH; i <= PASSWORD_MAX_LENGTH; i++) {
				try {
					startBruteForce(i, document);
				} catch (PasswordFoundInterrupter ex) {
					found = true;
					System.out.println("SUCCESS. PASSWORD: - "
							+ ex.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!found) {
				System.out.println("Failure - Password not found");
			}
			document.close();
			long timeTaken = System.currentTimeMillis() - startTime;
			System.out.println("TOTAL TIME TAKEN - " + timeTaken);
		} catch (IOException e) {
			System.out.println("PDF File not found at - " + PDF_FILE_PATH);
			throw new RuntimeException(e);
		}
	}

	public static void startBruteForce(int length, PDDocument document) {
		StringBuffer sb = new StringBuffer(length);
		char currentChar = fCharList[0];

		for (int i = 1; i <= length; i++) {
			sb.append(currentChar);
		}

		changeCharacters(0, sb, length, document);
	}

	private static StringBuffer changeCharacters(int pos, StringBuffer sb,
			int length, PDDocument document) {
		for (int i = 0; i <= fCharList.length - 1; i++) {
			sb.setCharAt(pos, fCharList[i]);
			if (pos == length - 1) {
				// Uncomment this to see the generated passwords. It may slow
				// down if you are trying for larger size passwords.
				// System.out.println(sb.toString());
				if (valid(sb.toString(), document)) {
					throw new BruteForceDecryptPDF.PasswordFoundInterrupter(
							sb.toString());
				}
			} else {
				changeCharacters(pos + 1, sb, length, document);
			}
		}

		return sb;
	}

	public static boolean valid(String password, PDDocument document) {
		System.out.println(password);
		try {
			document.decrypt(password);
			System.out.println("Success - " + password);
			return true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CryptographyException e) {
			return false;
		} catch (InvalidPasswordException e) {
			return false;
		}
	}

	private static class PasswordFoundInterrupter extends RuntimeException {
		public PasswordFoundInterrupter(String message) {
			super(message);
		}
	}
}
