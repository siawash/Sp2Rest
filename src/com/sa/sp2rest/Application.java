package com.sa.sp2rest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Application extends Element {
	
	private String pkg;	

	private List<OperationRepository> operationRepositories = new ArrayList<OperationRepository>();
	private List<Operator> operators = new ArrayList<Operator>();

	public String getPkg() {
		return this.pkg;
	}
	
	private Application(String name, String pkg, String url) {
		super(name, /* packageName, */url);
		this.pkg = pkg;
	}

	private boolean containsRepository(final String repositoryName) {
		return this.operationRepositories.stream().filter(o -> o.getName().equals(repositoryName)).findFirst().isPresent();
	}

	private boolean containsOperator(final String operatorName) {
		return this.operators.stream().map(Operator::getName).filter(operatorName::equals).findFirst().isPresent();
	}

	private OperationRepository getOperationReposiroty(final String repositoryName) {
		return this.operationRepositories.stream().filter(o -> o.getName().equals(repositoryName)).findFirst().get();
	}
	
	private Operator getOperator(final String opratorName) {
		return this.operators.stream().filter(o -> o.getName().equals(opratorName)).findFirst().get();
	}

	private static Application createApplication(String appName, String appPackage, String appUrl, String spListPath) throws SQLException {

		
		
		Application application = new Application(appName.trim(), appPackage.trim(), appUrl.trim());
		
		String[] items;		
		
//		DraftRegistration, deposite,/deposite/draft-registration ,DEV,JAMSHMA,BGIBPCR1,GetDraftRelatedInfo, /v1/get-draft-related-info
//		DraftRegistration,/deposite/draft-registration,DEV,JAMSHMA,BGIBPCR1,/v1/get-draft-related-info

		int errorIndex = -1;
		try (Stream<String> stream = Files.lines(Paths.get(spListPath), StandardCharsets.UTF_8)) {
			Object[] lines = stream.toArray();
			
			for (int i = 0; i < lines.length; i++) {
				errorIndex ++;
				
				Logger.log("processing line #" + (errorIndex + 1));
				
				if (Utility.isNullOrEmpty(lines[i].toString().trim()))
						continue;
				
				items = lines[i].toString().split(",");
				if (!application.containsRepository(items[OperationItemOrder.DB_NAME.ordinal()].trim())) {
					application.operationRepositories.add(new OperationRepository(items[OperationItemOrder.DB_NAME.ordinal()].trim()));
				}
				if (!application.containsOperator(items[OperationItemOrder.SERVICE_NAME.ordinal()].trim())) {
					application.operators
							.add(new Operator(items[OperationItemOrder.SERVICE_NAME
									.ordinal()].trim(), /* items[OperationItemOrder.SERVICE_PACKAGE.ordinal()].trim(),*/items[OperationItemOrder.SERVICE_URL.ordinal()].trim()));
				}

				Operation operation = new Operation(items[OperationItemOrder.DB_NAME.ordinal()].trim(),
						items[OperationItemOrder.SP_SHCEMA.ordinal()],
						items[OperationItemOrder.SP_NAME.ordinal()].trim(), /* items[OperationItemOrder.OPERATION_NAME.ordinal()].trim(), */items[OperationItemOrder.OPERATION_URL.ordinal()].trim());
				
				application.getOperationReposiroty(items[OperationItemOrder.DB_NAME.ordinal()].trim()).add(operation);
				application.getOperator(items[OperationItemOrder.SERVICE_NAME.ordinal()].trim()).add(operation);
				
			}

		} catch(java.nio.file.NoSuchFileException e) {
			Logger.log("error : " + e.getMessage() + " not found");
			Thread.currentThread().stop();
		} catch (IOException e) {
			Logger.log("error : " + e.getMessage());
//			JOptionPane.showMessageDialog(null, e.getMessage());
			Thread.currentThread().stop();
//			System.exit(0);
		} catch(Exception e) {
			
			Logger.log(" error " + (errorIndex > -1? "line " + (errorIndex + 1) : "") + " : " + e.getMessage());
			Thread.currentThread().stop();
		}

		return application;
	}

//
//	private static Application createApplication(final String path) throws SQLException {
//
//		Application application = null;
//		String[] items;
//		
//		try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
//			Object[] lines = stream.toArray();
//			for (int i = 0; i < lines.length; i++) {
//				if (Utility.isNullOrEmpty(lines[i].toString().trim()))
//						continue;
//				
//				if (application == null) {
//					items = lines[i].toString().split(",");
//					application = new Application(items[ApplicationItemOrder.APPLICATION_NAME.ordinal()].trim(), items[ApplicationItemOrder.APPLICATION_PACKAGE.ordinal()].trim(), items[ApplicationItemOrder.APPLICATION_URL.ordinal()].trim());
//				} else {
//
//					items = lines[i].toString().split(",");
//					if (!application.containsRepository(items[OperationItemOrder.DB_NAME.ordinal()].trim())) {
//						application.operationRepositories.add(new OperationRepository(items[OperationItemOrder.DB_NAME.ordinal()].trim()));
//					}
//					if (!application.containsOperator(items[OperationItemOrder.SERVICE_NAME.ordinal()].trim())) {
//						application.operators
//								.add(new Operator(
//										items[OperationItemOrder.SERVICE_NAME.ordinal()].trim(), /* items[OperationItemOrder.SERVICE_PACKAGE.ordinal()].trim(), */items[OperationItemOrder.SERVICE_URL.ordinal()].trim()));
//					}
//
//					Operation operation = new Operation(items[OperationItemOrder.DB_NAME.ordinal()].trim(),
//							items[OperationItemOrder.SP_SHCEMA.ordinal()],
//							items[OperationItemOrder.SP_NAME.ordinal()].trim(), /* items[OperationItemOrder.OPERATION_NAME.ordinal()].trim(), */items[OperationItemOrder.OPERATION_URL.ordinal()].trim());
//					
//					application.getOperationReposiroty(items[OperationItemOrder.DB_NAME.ordinal()].trim()).add(operation);
//					application.getOperator(items[OperationItemOrder.SERVICE_NAME.ordinal()].trim()).add(operation);
//				}
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return application;
//	}
//
	private void initRepositories() {
		this.operationRepositories.forEach(repository -> {
			try {
				repository.init();
			} catch (Exception e) {
				Logger.log(e.getMessage());
//				JOptionPane.showMessageDialog(null, e.getMessage());
				Thread.currentThread().stop();
//				System.exit(0);
			}
		});
	}
	
//	private void initRepositories(String filePath) {
//		
//		String path = new File(filePath).getParent();
//		
//		this.operationRepositories.forEach(repository -> {
//			try {
//				repository.init(path);
//			} catch (Exception e) {
//				Logger.log(e.getMessage());
//				JOptionPane.showMessageDialog(null, e.getMessage());
//				System.exit(0);
//			}
//		});
//	}

//	private void printRepositories() {
//		this.operationRepositories.forEach(orep -> Logger.log("*** " + orep.getName() + " - " + orep.getOperations().size()));
//	}
//
//	private void printOperatorWithInside() {
//		this.operators.forEach(opr -> {
//			Logger.log("*** %s  %s  %s  %s".formatted(opr.getName(), /* opr.getPkg(), */opr.getUrl(), opr.getOperations().size()));
//		});
//	}
		
//	@SuppressWarnings("unused")
//	private void print4check() {
//
//		printRepositories();
//		printOperatorWithInside();
//	}
	
	public static boolean evaluateSpList(String path) {
		
		boolean result = true;
		
		
		
		return result;
	}

//	public static Application getInstance(String path) throws SQLException {
//		
//		Application application = createApplication(path);
//		
////		application.initRepositories();
//		application.initRepositories(path);
//		
//		Control control = new Control(application , false, application.operationRepositories, application.operators);
//		
//		control.makeFiles();
//		
////		application.print4check();
//		return application;
//	}
	
	public static Application getInstance(String appName, String appPackage, String appUrl, String spListPath) throws SQLException {
		
		Application application = createApplication(appName, appPackage, appUrl, spListPath);
		
		application.initRepositories();
		
		File file = new File(spListPath);
		String resultPath = file.getParent();
		
		Control control = new Control(application , false, application.operationRepositories, application.operators, resultPath);
		
		control.makeFiles();
		
//		application.print4check();
		return application;
	}
}
