package com.udd.naucnacentrala.config;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.Authority;
import com.udd.naucnacentrala.domain.Magazine;
import com.udd.naucnacentrala.domain.PaymentRecord;
import com.udd.naucnacentrala.domain.PaymentType;
import com.udd.naucnacentrala.domain.ScientificArea;
import com.udd.naucnacentrala.domain.ScientificPaper;
import com.udd.naucnacentrala.domain.Subscription;
import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.domain.UserRole;
import com.udd.naucnacentrala.repository.AuthoritiesRepository;
import com.udd.naucnacentrala.repository.MagazineRepository;
import com.udd.naucnacentrala.repository.PaymentRecordRepository;
import com.udd.naucnacentrala.repository.ScientificAreaRepository;
import com.udd.naucnacentrala.repository.ScientificPaperRepository;
import com.udd.naucnacentrala.repository.SubscriptionRepository;
import com.udd.naucnacentrala.repository.UserRepository;

@Component
@SuppressWarnings("unused")
public class DataLoader implements ApplicationRunner {

	@Autowired
	private ScientificAreaRepository scientificAreaRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MagazineRepository magazineRepository;
	
	@Autowired
	private ScientificPaperRepository scientificPaperRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private PaymentRecordRepository paymentRecordRepository;
	
	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
		insertIntoScientificArea();
		insertIntoUser();
		insertIntoMagazine();
		insertIntoScientificPaper();
		insertIntoSubscription();	
	}


	public static String getText(PDFParser parser) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return null;
	}
	
	public String getText(File file) {
		try {
			PDFParser parser = new PDFParser( new RandomAccessFile(file, "r"));
			parser.parse();
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private void insertIntoSubscription() {

		Subscription s1 = new Subscription();
		
		s1.setStartingDate(new Date(10, 10, 2018));
		s1.setEndingDate(new Date(12, 12, 2018));
		s1.setPrice(2d);
		s1.setUser(userRepository.getOne(1l));
		s1.setMagazine(magazineRepository.getOne(1l));
		
		subscriptionRepository.save(s1);
		
		Subscription s2 = new Subscription();
		
		s2.setStartingDate(new Date(5, 5, 2018));
		s2.setEndingDate(new Date(5, 5, 2018));
		s2.setPrice(3d);
		s2.setUser(userRepository.getOne(2l));
		s2.setMagazine(magazineRepository.getOne(2l));
		
		subscriptionRepository.save(s2);
		
	}

	private void insertIntoPaymentRecord() {

		PaymentRecord pr1 = new PaymentRecord();
		
		pr1.setUser(userRepository.getOne(1l));
		pr1.setPaymentType(PaymentType.SUBSCRIPTION);
		pr1.setPayedItemID("11111111");
		
		paymentRecordRepository.save(pr1);
		
		
	}

	private void insertIntoScientificPaper() {
		
		ScientificPaper sp1 = new ScientificPaper();
		
		sp1.setAbstractDescription("Abstract description of the paper");
		sp1.setKeywords("science,geometry");
		sp1.setPdf("pdf1");
		sp1.setTitle("Gauses trigonometry");
		sp1.setAuthor(userRepository.getOne(1l));
		sp1.setMagazine(magazineRepository.getOne(1l));
		sp1.setScientificArea(scientificAreaRepository.getOne(1l));
		
		Set<User> coAuthors1 = new HashSet<User>();
		
		coAuthors1.add(userRepository.getOne(2l));
		sp1.setCoAuthors(coAuthors1);
		
		scientificPaperRepository.save(sp1);
		
		ScientificPaper sp2 = new ScientificPaper();
		
		sp2.setAbstractDescription("Very abstract description");
		sp2.setKeywords("lions,animals");
		sp2.setPdf("pdf2");
		sp2.setTitle("Lions in the wild");
		sp2.setAuthor(userRepository.getOne(1l));
		sp2.setMagazine(magazineRepository.getOne(2l));
		sp2.setScientificArea(scientificAreaRepository.getOne(3l));
		
		Set<User> coAuthors2 = new HashSet<User>();
		
		coAuthors2.add(userRepository.getOne(2l));
		sp2.setCoAuthors(coAuthors2);
		
		scientificPaperRepository.save(sp2);
		
		
	}

	private void insertIntoUser() {
		
		BCryptPasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
		
		User u1 = new User();
		
		u1.setCity("Backa Topola");
		u1.setEmail("aco.degenek@yahoo.com");
		u1.setPassword(passwordEncoder1.encode("aki123"));
		u1.setFirstName("Arsenije");
		u1.setLastName("Degenek");
		u1.setState("Srbija");
		u1.setTitle("Doktor nauka");
		
		List<Authority> authorities = new ArrayList<Authority>();
		
		Authority a1 = new Authority();
		a1.setName(UserRole.AUTHOR);
		authoritiesRepository.save(a1);
		
		Authority a2 = new Authority();
		a2.setName(UserRole.READER);
		authoritiesRepository.save(a2);
		
		authorities.add(authoritiesRepository.getOne(1l));
		authorities.add(authoritiesRepository.getOne(2l));
		u1.setAuthorities(authorities);
		
		Set<ScientificArea> scientificAreas1 = new HashSet<ScientificArea>();
		
		scientificAreas1.add(scientificAreaRepository.getOne(1l));
		scientificAreas1.add(scientificAreaRepository.getOne(2l));
		
		u1.setScientificAreas(scientificAreas1);
		
		userRepository.save(u1);
		
		User u2 = new User();
		
		u2.setCity("Beograd");
		u2.setEmail("marko@gmail.com");
		u2.setPassword(passwordEncoder1.encode("123"));
		u2.setFirstName("Marko");
		u2.setLastName("Citalac");
		u2.setState("Srbija");
		u2.setTitle(null);
		
		List<Authority> authorities2 = new ArrayList<Authority>();
		
		authorities2.add(authoritiesRepository.getOne(2l));
		u2.setAuthorities(authorities2);

		Set<ScientificArea> scientificAreas2 = new HashSet<ScientificArea>();
		
		scientificAreas2.add(scientificAreaRepository.getOne(2l));
		scientificAreas2.add(scientificAreaRepository.getOne(3l));
		
		u2.setScientificAreas(scientificAreas2);
		
		userRepository.save(u2);
		
		User u3 = new User();
		
		u3.setCity("Novi sad");
		u3.setEmail("aco.degenek@gmail.com");
		u3.setPassword(passwordEncoder1.encode("aki123"));
		u3.setFirstName("Mitar");
		u3.setLastName("Mitrovic");
		u3.setState("Srbija");
		u3.setTitle(null);
		u3.setScientificAreas(null);
		
		List<Authority> authorities3 = new ArrayList<Authority>();
		Authority a3 = new Authority();
		a3.setName(UserRole.EDITOR);
		authoritiesRepository.save(a3);
		
		authorities3.add(authoritiesRepository.getOne(3l));
		u3.setAuthorities(authorities3);
		
		userRepository.save(u3);
		
		User u4 = new User();
		
		u4.setCity("Kragujevac");
		u4.setEmail("djuro.degenek@gmail.com");
		u4.setPassword(passwordEncoder1.encode("aki123"));
		u4.setFirstName("Marija");
		u4.setLastName("Markovic");
		u4.setState("Srbija");
		u4.setTitle(null);
		u4.setScientificAreas(null);
		
		List<Authority> authorities4 = new ArrayList<Authority>();
		Authority a4 = new Authority();
		a4.setName(UserRole.REVIEWER);
		authoritiesRepository.save(a4);
		
		authorities4.add(authoritiesRepository.getOne(4l));
		u4.setAuthorities(authorities4);
		
		userRepository.save(u4);
		
		User u5 = new User();
		
		u5.setCity("Nis");
		u5.setEmail("milansuvajdzic@yahoo.com");
		u5.setPassword(passwordEncoder1.encode("aki123"));
		u5.setFirstName("Milica");
		u5.setLastName("Milinkovic");
		u5.setState("Srbija");
		u5.setTitle(null);
		u5.setScientificAreas(null);
		
		List<Authority> authorities5 = new ArrayList<Authority>();
		Authority a5 = new Authority();
		a5.setName(UserRole.REVIEWER);
		authoritiesRepository.save(a5);
		
		authorities4.add(authoritiesRepository.getOne(5l));
		u5.setAuthorities(authorities5);
		
		userRepository.save(u5);
		
	}

	private void insertIntoMagazine() {

		Magazine m1 = new Magazine();
		
		m1.setName("Ancient Greek");
		m1.setISSN(12345678);
		m1.setPaymentType(PaymentType.OPENACCESS);
		m1.setPrice((double)15);
		m1.setMainEditor(userRepository.getOne((long)3));
		m1.setMerchantId("merchantID1");
		
		Set<User> reviewers1 = new HashSet<User>();
		
		User u1 = userRepository.getOne((long)4);
		User u2 = userRepository.getOne((long)5);
		
		reviewers1.add(u1);
		reviewers1.add(u2);
		
		m1.setReviewers(reviewers1);
		
		List<ScientificArea> scientificAreas1 = new ArrayList<ScientificArea>();
		
		scientificAreas1.add(scientificAreaRepository.getOne((long)1));
		
		m1.setScientificAreas(scientificAreas1);
		
		Set<User> editorsOfSpecialAreas = new HashSet<User>();

		editorsOfSpecialAreas.add(userRepository.getOne((long)3));
		
		m1.setEditorsOfSpecificAreas(editorsOfSpecialAreas);
		
		magazineRepository.saveAndFlush(m1);
		
		Magazine m2 = new Magazine();
		
		m2.setName("Mac World");
		m2.setISSN(87654321);
		m2.setPaymentType(PaymentType.SUBSCRIPTION);
		m2.setPrice((double)5);
		m2.setMainEditor(userRepository.getOne((long)3));
		m2.setMerchantId("merchantID2");

		
		Set<User> reviewers2 = new HashSet<User>();
		Set<User> editorsOfSpecialAreas2 = new HashSet<User>();
		List<ScientificArea> scientificAreas2 = new ArrayList<ScientificArea>();
		
		reviewers2.add(userRepository.getOne((long)4));
		editorsOfSpecialAreas2.add(userRepository.getOne((long)3));
		scientificAreas2.add(scientificAreaRepository.getOne((long)2));
		
		m2.setReviewers(reviewers2);
		m2.setScientificAreas(scientificAreas2);
		m2.setEditorsOfSpecificAreas(editorsOfSpecialAreas2);

		magazineRepository.saveAndFlush(m2);
		
		Magazine m3 = new Magazine();
		
		m3.setName("Astronomy");
		m3.setISSN(12348765);
		m3.setPaymentType(PaymentType.OPENACCESS);
		m3.setPrice((double)25);
		m3.setMainEditor(userRepository.getOne((long)3));
		m3.setMerchantId("merchantID3");
		
		Set<User> reviewers3 = new HashSet<User>();
		
		reviewers3.add(u1);
		reviewers3.add(u2);
		
		m3.setReviewers(reviewers1);
		
		List<ScientificArea> scientificAreas3 = new ArrayList<ScientificArea>();
		
		scientificAreas3.add(scientificAreaRepository.getOne((long)3));
		
		m3.setScientificAreas(scientificAreas3);
		
		Set<User> editorsOfSpecialAreas3 = new HashSet<User>();

		editorsOfSpecialAreas3.add(userRepository.getOne((long)3));
		
		m3.setEditorsOfSpecificAreas(editorsOfSpecialAreas3);
		
		magazineRepository.saveAndFlush(m3);
			
	}

	private void insertIntoScientificArea() {
		
		ScientificArea sa1 = new ScientificArea();
		ScientificArea sa2 = new ScientificArea();
		ScientificArea sa3 = new ScientificArea();
		
		sa1.setScientificAreaName("History");
		sa1.setEditor(2l);
		sa2.setScientificAreaName("Computing");
		sa2.setEditor(2l);
		sa3.setScientificAreaName("Astronomy");
		sa3.setEditor(2l);
		
		scientificAreaRepository.save(sa1);
		scientificAreaRepository.save(sa2);
		scientificAreaRepository.save(sa3);
	}

}
