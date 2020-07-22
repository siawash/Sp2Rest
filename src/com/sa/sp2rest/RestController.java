package com.sa.sp2rest;


public class RestController extends Clazz {
	
	public RestController(String applicationPkg, Operator operator) {
		
		this.name = operator.getName() + "Rest";
		this.pkg = applicationPkg + ".gateway";
		this.imports.add("org.springframework.beans.factory.annotation.Autowired");
		this.imports.add("org.springframework.web.bind.annotation.RequestMapping");
		this.imports.add("org.springframework.web.bind.annotation.RestController");
		this.imports.add("org.springframework.web.bind.annotation.RequestBody");
		this.imports.add("io.jsonwebtoken.Claims");
		this.imports.add("org.springframework.web.bind.annotation.PostMapping");
		this.imports.add("org.springframework.web.bind.annotation.RequestAttribute");
		this.imports.add("springfox.documentation.annotations.ApiIgnore");
		this.imports.add("io.swagger.annotations.Api");
		this.imports.add("io.swagger.annotations.ApiOperation");
//		this.imports.add("io.swagger.annotations.ApiImplicitParam");

		operator.getOperations().forEach(operation -> {
			if (!operation.getInputParameters().isEmpty())
				this.imports.add(applicationPkg + ".dto." + operation.getName() + "Request");
			
			if (!operation.getOutputParameters().isEmpty())
				this.imports.add(applicationPkg + ".dto." + operation.getName() + "Response");
		});

		
		
		this.imports.add(applicationPkg + ".service." + operator.getName() + "Service");

		this.annotations.add("@Api(value = \"" + this.name + "\")");
		this.annotations.add("@RestController");
		this.annotations.add("@RequestMapping(\"" + operator.getUrl() + "\")");

		this.injections.add(new Injection("@Autowired", "private " + operator.getName() + "Service " + operator.getName() + "Service"));

		operator.getOperations().forEach(operation -> {
			
			Method method = new Method();
			method.getAnnotations().add("@ApiOperation(tags = \"" + operator.getName() + "\", value = \"" + /* operation.getPkg() + */" - " + operation.getName() + "\", produces=\"application/json\", consumes = \"application/json\")");
//			method.getAnnotations().add("@ApiImplicitParam(name = \"IsamToken\", value = \"Enter the token sent by Isam server\", required = true, dataType = \"String\", paramType = \"header\")");
			method.getAnnotations().add("@PostMapping(value = \"" + operation.getUrl() + "\")");
			method.setAccessor(Accessor.Public);
			method.setReturnType(operation.getName() + "Response");
			method.setName(operation.getName());

			if (!operation.getInputParameters().isEmpty()) {
				method.setParameter("@RequestBody " + operation.getName() + "Request request, @ApiIgnore @RequestAttribute Claims jwt");
				method.getBody().add("\trequest.setTokenInfo(jwt);");
				method.getBody().add("\treturn " + operator.getName() + "Service." + operation.getName() + "(request);");
			} else {
				method.getBody().add("\treturn " + operator.getName() + "Service." + operation.getName() + "();");
			}

			method.getBody().add("}\r\n");

			this.methods.add(method);
		});
	}
}