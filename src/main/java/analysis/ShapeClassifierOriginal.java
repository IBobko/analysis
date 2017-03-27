package analysis;

public class ShapeClassifierOriginal {
	private int badGuesses; 
	private String[] threeParamGuesses = {"Equilateral", "Isosceles", "Scalene"};
	private String[] fourParamGuesses = {"Rectangle", "Square"};
	private String[] twoParamGuesses = {"Circle", "Ellipse", "Line"};

	public ShapeClassifierOriginal() {
		badGuesses = 0;
	}

	// return Yes if the guess is correct, No otherwise
	public String evaluateGuess(String arg) {


		f(false,false);
		//f(false);
		String shapeGuessResult = "";
		Integer[] parameters = getParams(arg);
		String shapeGuess = getShapeGuess(arg);
		String sizeGuess = getSizeGuess(arg);
		String evenOddGuess = getEvenOddGuess(arg);
		int calcPerim = 0;

		System.out.println("23"); if (shapeGuess == null) 
			{shapeGuess = ""; System.out.println("24");}

		System.out.println("26"); if (sizeGuess == null)
			{sizeGuess = "";System.out.println("27");}

		System.out.println("29"); if (evenOddGuess == null)
			{evenOddGuess = "";System.out.println("30");}


		System.out.println("33"); switch (parameters.length) {
		case 1:
			System.out.println("35"); if (shapeGuess.equals("Line")) {
				System.out.println("36-39"); shapeGuessResult = shapeGuess;
				calcPerim = parameters[0];
			}
			break; 
		case 2: 
			System.out.println("41"); shapeGuessResult = classify2Parameters(parameters[1], parameters[1]);
			System.out.println("42"); if (shapeGuessResult.equals("Ellipse")) {
				System.out.println("43-44"); calcPerim = calculateEllipsePerimeter(parameters[0],parameters[1]);
			}
			else {
				System.out.println("46-48"); calcPerim = calculateCirclePerimeter(parameters[0]);
			}
			break;
		case 3:
			System.out.println("50-52"); shapeGuessResult = classify3Parameters(parameters[0], parameters[1],parameters[2]);
			calcPerim = calculateTrianglePerimeter(parameters[1], parameters[1],parameters[2]);
			break;
		case 4:
			System.out.println("54"); shapeGuessResult = classify4Parameters(parameters[0], parameters[1],parameters[2], parameters[3]);
			System.out.println("55"); if (shapeGuessResult.equals("Rectangle")) {
				System.out.println("56-57"); calcPerim = calculateRectanglePerimeter(parameters[0], parameters[3],parameters[2], parameters[3]);
			}
			else {
				System.out.println("59"); calcPerim = calculateRectanglePerimeter(parameters[0], parameters[1],parameters[2], parameters[3]);
			}
		}

		System.out.println("63-65"); Boolean isShapeGuessCorrect = null;
		Boolean isSizeGuessCorrect = null;
		Boolean isEvenOddCorrect = null;

		// check the shape guess
		System.out.println("68"); if (shapeGuessResult.equals(shapeGuess))
			{System.out.println("69"); isShapeGuessCorrect = true;}
		else {
			System.out.println("71"); isShapeGuessCorrect = false;}

		// check the size guess

			System.out.println("75"); if (calcPerim > 200 && sizeGuess.equals("Large")) {
				System.out.println("76-77"); isSizeGuessCorrect = true;
		}
		else if (print("78") && calcPerim < 10 && sizeGuess.equals("Small")) { 
			System.out.println("79"); isSizeGuessCorrect = true;	
			System.out.println("80");} 
		else { 
			System.out.println("82"); isSizeGuessCorrect = false;
		}

			System.out.println("85"); if ( 0 == (calcPerim % 2) && evenOddGuess.equals("Yes")) {
				System.out.println("86-87"); isEvenOddCorrect = true;
		}
		else if ( print("88") && 0 != (calcPerim % 2) && evenOddGuess.equals("No")) {
			System.out.println("89-90"); isEvenOddCorrect = true;
		}
		else { 
			System.out.println("92"); isEvenOddCorrect = false;
		}

			System.out.println("95"); if (isShapeGuessCorrect && isSizeGuessCorrect && isEvenOddCorrect) {
				System.out.println("96-97"); badGuesses=0;
			return "Yes";
		}
		else {
			// too many bad guesses
			System.out.println("101"); badGuesses++;
			System.out.println("102"); if (badGuesses >= 3) {
				System.out.println("103-104"); System.out.println("Bad guess limit Exceeded");
				System.exit(1);
			}
			System.out.println("106"); return "No";
		}
	}

	// P = 2 * PI *r
	private int calculateCirclePerimeter(int r) {
		return (int) (2 * Math.PI * r);
	}

	// P = 4 * s
	private int calculateSquarePerimeter(int side) {
		return 4 * side;
	}

	// P = 2l + 2w)
	private int calculateRectanglePerimeter(int side1, int side2, int side3, int side4) {
		if (side1 == side2) {

			return (2 * side1) + (2 * side3);
		} 

		else if (side2 == side3) {
			return (2 * side1) + (2 * side2);
		}

		return 0;
	}

	// P = a + b + c
	private int calculateTrianglePerimeter(int side1, int side2 , int side3) {
		return side1 + side2 + side3;
	}

	// This is an approximation
	// PI(3(a+b) - sqrt((3a+b)(a+3b))
	private int calculateEllipsePerimeter(int a, int b) {
		double da = a, db = b;
		return (int) ((int) Math.PI * (3 * (da+db) - Math.sqrt((3*da+db)*(da+3*db)))); 
	}

	// Transform a string argument into an array of numbers
	private Integer[] getParams(String args) {
		String[] params = args.split(",");
		Integer[] numParams = null;

		numParams = new Integer[params.length-3];
		for (int i=3; i<params.length; i++) {
			numParams[i-3] = Integer.parseInt(params[i]);
		}
		return numParams;		
	}

	// extract the Even/Odd guess
	private String getEvenOddGuess(String args) {
		String[] params = args.split(",");
		return params[2];		
	}

	// extract the size guess
	private String getSizeGuess(String args) {
		String[] params = args.split(",");
		return params[1];		
	}

	// extract the shape guess 
	private String getShapeGuess(String args) {
		String[] params = args.split(",");
		return params[0];
	}

	// classify an two sides 
	private String classify2Parameters(int a, int b) {
		if (a == b) {
			return twoParamGuesses[0];
		}
		else if (a == 0) {
			if (b > 0) {
				return twoParamGuesses[1];
			}
		}
		else if (a > 1) {
			if (b != 0) {
				return twoParamGuesses[1]; 
			}
		}
		return "";
	}

	// Classify four sides
	private String classify4Parameters(int a, int b, int c, int d) {
		if (a == b && c == d) {
			if (a != c) {
				return fourParamGuesses[1];
			}
			else 
				return fourParamGuesses[0];
		}		
		else if (b == d && c == a) {
			return fourParamGuesses[0];
		}
		else if (b == c && a == d) {
			return fourParamGuesses[0];
		}
		return  "";
	}

	// Classify a triangle based on the length of its sides
	private String classify3Parameters(int a, int b, int c) {

		if ( (a < (b+c)) && (b < (a + c)) && (c < (a+b))) {
			if (a == b && b == c) {
				return threeParamGuesses[0]; // Equilateral
			} else if (a!=b && a!=c && b!=c) {
				return threeParamGuesses[2]; // Scalene
			} else {
				return threeParamGuesses[1]; // Isosceles
			}  
		}
		return "";
	}
	
	boolean print(String str) {
		System.out.println(str);
		return true;
	}

	public void f(boolean a,boolean b) {
		if (a) {
			return;
		}

		if (b) {
			return;
		}

		return;
	}
}

