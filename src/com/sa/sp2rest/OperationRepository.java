package com.sa.sp2rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

public class OperationRepository {

	private String name;
	private String driver;
	private String url;
	private String username;
	private String password;

	private List<Operation> operations = new ArrayList<Operation>();

	public String getName() {
		
		return this.name;
	}
	
	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void add(Operation operation) {
		
		this.operations.add(operation);		
	}
	
	public  List<Operation> getOperations() {
		return this.operations;
	}

	public OperationRepository(String repositoryName) {

		this.name = repositoryName;
		String name = this.name.toLowerCase();
		
		try {
			Map<String, String> props = Config.getRepositoryProperties(name);

			driver = props.get("driver");
			url = props.get("url");
			username = props.get("username");
			password = props.get("password");
			Class.forName(this.driver);
			
//			System.out.println("initialization for " + this.name + " done!");
		} catch (IOException ex) {
//			Logger.log("can not read db2.properties file");
//			System.out.println("can not read db2.properties file");

			Logger.log("There is something wrong with config.properties");
//			JOptionPane.showMessageDialog(null, "There is something wrong with config.properties");
			Thread.currentThread().stop();
//			System.exit(0);
		} catch (ClassNotFoundException e) {
//			System.out.println("data base driver not found");
//			Logger.log("data base driver not found");

			Logger.log("data base driver not found");
//			JOptionPane.showMessageDialog(null, "data base driver not found");
			Thread.currentThread().stop();
//			System.exit(0);
		} catch(Exception e) {
			Logger.log("data base driver could not be loaded");
//			JOptionPane.showMessageDialog(null, "data base driver not found");
			Thread.currentThread().stop();
//			System.exit(0);
		}
	}
	
	public void initOperations() throws ClassNotFoundException, SQLException, IOException {
	
////		System.out.println("** connecting to %s".formatted(this.url));
//		Logger.log("connecting to %s".formatted(this.url));
		Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
		Statement statement = connection.createStatement();
		
		this.operations.forEach(operation -> {
			Logger.log("initiating Operation " + operation.getName());
			String query = String.format("SELECT R.NAME, R.RESULT_SETS, R.SCHEMA FROM SYSIBM.SYSROUTINES R WHERE R.NAME ='%s' AND R.SCHEMA = '%s' WITH UR;", operation.getName(), operation.getSchema());
			ResultSet rs = null;
			try {
				rs = statement.executeQuery(query);
				rs.next();
				operation.setResultSetCount(rs.getShort("RESULT_SETS"));
			} catch (SQLException e) {
//				System.out.println(e.getMessage());
//				Logger.log(e.getMessage());
				Logger.log("error in " + operation.getSchema() + "." + operation.getName() + ", check if sp exists in the specified schema - exception: " + e.getMessage());
//				JOptionPane.showMessageDialog(null, e.getMessage());
				Thread.currentThread().stop();
//				System.exit(0);
			}
//			finally {
//				if (rs != null)
//					try {
//						rs.close();
//						statement.close();
//						connection.close();
//					} catch (SQLException e) {
//						System.out.println(e.getMessage());
//					}
//			}
		});
	}


	public void initOperationsParameters() throws ClassNotFoundException, SQLException {
	
		Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
		Statement statement = connection.createStatement();
		
		this.operations.forEach(operation -> {
			Logger.log("initiating Operation parameters for " + operation.getName());
			String query = String.format("SELECT P.SPECIFICNAME AS SPNAME, P.ORDINAL, P.PARMNAME AS PARAMNAME, P.ROWTYPE AS  DIRECTION, P.TYPENAME AS TYPE, P.LENGTH, P.SCALE FROM SYSIBM.SYSPARMS P WHERE P.NAME ='%s' AND P.SCHEMA = '%s' ORDER BY P.SPECIFICNAME, P.ORDINAL WITH UR;", operation.getName(), operation.getSchema());
			ResultSet rs = null;
			try {
				rs = statement.executeQuery(query);
				while (rs.next()) {
	
					OperationParameter param = new OperationParameter();
					param.setName(rs.getString("PARAMNAME"));
					param.setDirection(rs.getString("DIRECTION"));
					param.setType(rs.getString("TYPE"));
					param.setLength(rs.getInt("LENGTH"));
					param.setScale(rs.getShort("SCALE"));
	
					operation.add(param);
				}
			} catch(Exception e) {
				Logger.log("error in " + operation.getSchema() + "." + operation.getName() + ", check if sp exists in the specified schema - exception: " + e.getMessage());
//				JOptionPane.showMessageDialog(null, e.getMessage());
				Thread.currentThread().stop();
//				System.exit(0);
			} 
		});
	}


	private Operation getOperation(final String opratorationName) {
		return this.operations.stream().filter(o -> o.getName().equals(opratorationName)).findFirst().get();
	}


	// for initiating from files
	public void initOperations(String path) {
		
		path = path + "\\routines\\" + this.name + ".routines.csv";
				
		try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
			stream.forEach(line -> {
				String[] items = line.split(",");
				this.getOperation(items[0].trim()).setResultSetCount(Short.valueOf(items[2]));
			});
		} catch(Exception e) {
//			Logger.log(e.getMessage());
//			System.out.println(e.getMessage());

			Logger.log(e.getMessage());
//			JOptionPane.showMessageDialog(null, e.getMessage());
			Thread.currentThread().stop();
//			System.exit(0);
		}
	}


	// for initiating from files
	public void initOperationsParameters(String path) throws ClassNotFoundException, SQLException {
		
		path = path + "\\params\\" + this.name + ".params.csv"; 
		
		try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
			stream.forEach(line -> {
				String[] items = line.split(",");
				Operation operation = this.getOperation(items[0].trim());
				OperationParameter param = new OperationParameter();
				param.setName(items[2].trim());
				param.setDirection(items[3].trim());
				param.setType(items[4]);
				param.setLength(Integer.valueOf(items[5].trim()));
				param.setScale(Short.valueOf(items[6].trim()));
				operation.add(param);
			});
		} catch(Exception e) {
//			Logger.log(e.getMessage());
//			System.out.println(e.getMessage());
			
			Logger.log(e.getMessage());
//			JOptionPane.showMessageDialog(null, e.getMessage());
			Thread.currentThread().stop();
//			System.exit(0);
		}
		
	}




	public void init() throws ClassNotFoundException, SQLException, IOException {
		
		initOperations();
		initOperationsParameters();		
	}


	public void init(String path) throws ClassNotFoundException, SQLException, IOException {

		initOperations(path);
		initOperationsParameters(path);		
	}

}
