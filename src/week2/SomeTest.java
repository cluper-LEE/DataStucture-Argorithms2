package week2;

public class SomeTest {

	public static void main(String[] args) {
		
		// Checking Prime Number
		
		int n=523;
		int m= n/2;
		for (int i=2; i<m; i++){
			float x=(float)n/i;
			int y=n/i;
			if (x==y) {
				System.out.println(">> "+n+" can be devided by "+ i);
			}
		}
		System.out.println(">> if there is no devider, "+n+" is a Prime number ");
		
		// Key Conversion into Integer
		
		int x = (int)'A';
		System.out.println("\n>> A = "+x);
		x = (int)'b';
		System.out.println("\n>> b = "+x);
		
		String item = "This is a Test!";
		
		double d=0;
		for (int i=0; i<item.length(); i++) {
			d= d+(int)item.charAt(i);
		}
		System.out.println("\n>> This is a Test! [addition] = "+d);
		
		d=0;
		for (int i=0; i<item.length(); i++) {
			d= d+item.charAt(i)*Math.pow(31,i);
		}
		System.out.println("\n>> This is a Test! [polynomial]= "+d);
		
		
		// °öÇÏ±â ¹æ¹ý
		int tableSize=17;
		for (int i=1; i<=10; i++) {
			d = 100+i;
			double temp = (double)d*0.6180339887;
			double res = temp - Math.floor(temp); 
			System.out.println("\n>> key = "+d+"  res = "+res +" ==> Hashcode = "+(int)(res*tableSize));
		}

		

	}

}
