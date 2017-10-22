package Sample;

import java.io.*;

import FlexFT.StateInterface;
import FlexFT.ContextInterface;
import OpenCOM.ILifeCycle;
import OpenCOM.IOpenCOM;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOM;
import Sample.Add.AddRB;
import Sample.Add.IAdd;
import Sample.Add.RightAdd;
import Sample.Add.WrongAdd;
import Sample.Calculator.Calculator;
import Sample.Calculator.ICalculator;
import Sample.Connection.ZigBeeConnection;
import Sample.Connection.ConnectionState;
import Sample.Connection.IConnection;
import Sample.Connection.WiFiConnection;
import Sample.Divide.DivideNV;
import Sample.Divide.IDivide;
import Sample.Divide.RightDivide;
import Sample.Divide.WrongDivide;
import Sample.Multiply.IMultiply;
import Sample.Multiply.MultiplyNV;
import Sample.Multiply.MultiplyVariant;
import Sample.Multiply.PlusVariant;
import Sample.Multiply.WrongMultiply;
import Sample.Sort.GeradorDeArranjos;
import Sample.Sort.ISort;
import Sample.Sort.InverteSort;
import Sample.Sort.NothingSort;
import Sample.Sort.SelectionSort;
import Sample.Sort.SortRB;
import Sample.Subtract.ISubtract;
import Sample.Subtract.RightSubtract;
import Sample.Subtract.SubtractContext;
import Sample.Subtract.WrongSubtract;

public class Application {

	public static void main(String[] args) throws IOException,
			InterruptedException {



		File file = new File("/home/pi/inicio.txt");
                try {
                        if (file.createNewFile()) {
                                System.out.println("File named created successfully !");
                        } else {
                                System.out.println("File with name already exixts !");
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }

                int count = 0;
                while (count !=10){
                count++;
                Thread.sleep(1000);
                }

                try {
                        if (file.delete()) {
                                System.out.println("File deleted successfully !");
                        } else {
                                System.out.println("File delete operation failed !");
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }







		// Create the OpenCOM runtime & Get the IOpenCOM interface reference

		OpenCOM runtime = new OpenCOM();
		IOpenCOM pIOCM = (IOpenCOM) runtime.QueryInterface(IOpenCOM.class
				.getName());

		// Create the AddRB component

		IUnknown pAddRBIUnk = (IUnknown) pIOCM.createInstance(AddRB.class
				.getName(), "AddRB");

		ILifeCycle pILife = (ILifeCycle) pAddRBIUnk
				.QueryInterface(ILifeCycle.class.getName());
		pILife.startup(pIOCM);

		// Create the Add Components
		
		for (int i = 1; i <= 2; i++) {
			IUnknown pAddIUnk;
			if (i < 2) {
				pAddIUnk = (IUnknown) pIOCM.createInstance(WrongAdd.class
						.getName(), "Add " + i);
			} else {
				pAddIUnk = (IUnknown) pIOCM.createInstance(RightAdd.class
						.getName(), "Add " + i);
			}
			pILife = (ILifeCycle) pAddIUnk.QueryInterface(ILifeCycle.class
					.getName());
			pILife.startup(pIOCM);
			runtime.connect(pAddRBIUnk, pAddIUnk, IAdd.class.getName());
		}
		
		// Create the SortRB component

		IUnknown pSortRBIUnk = (IUnknown) pIOCM.createInstance(SortRB.class
				.getName(), "SortRB");

		pILife = (ILifeCycle) pSortRBIUnk.QueryInterface(ILifeCycle.class
				.getName());
		pILife.startup(pIOCM);

		// Create the Sort Components
		
		for (int i = 1; i <= 3; i++) {
			IUnknown pSortIUnk;
			if (i == 1) {
				pSortIUnk = (IUnknown) pIOCM.createInstance(NothingSort.class
						.getName(), "Sort " + i);
			} else if (i == 2) {
				pSortIUnk = (IUnknown) pIOCM.createInstance(InverteSort.class
						.getName(), "Sort " + i);
			} else {
				pSortIUnk = (IUnknown) pIOCM.createInstance(SelectionSort.class
						.getName(), "Sort " + i);
			}
			pILife = (ILifeCycle) pSortIUnk.QueryInterface(ILifeCycle.class
					.getName());
			pILife.startup(pIOCM);
			runtime.connect(pSortRBIUnk, pSortIUnk, ISort.class.getName());
		}
		
		// Create the MultiplyNV component

		IUnknown pMulNVIUnk = (IUnknown) pIOCM.createInstance(MultiplyNV.class
				.getName(), "MultiplyNV");

		pILife = (ILifeCycle) pMulNVIUnk.QueryInterface(ILifeCycle.class
				.getName());
		pILife.startup(pIOCM);

		// Create the Multiply Components

		for (int i = 1; i <= 3; i++) {
			IUnknown pMulIUnk;
			if (i == 1) {
				pMulIUnk = (IUnknown) pIOCM.createInstance(
						MultiplyVariant.class.getName(), "Multiply " + i);
			} else if (i == 2) {
				pMulIUnk = (IUnknown) pIOCM.createInstance(PlusVariant.class
						.getName(), "Multiply " + i);
			} else {
				pMulIUnk = (IUnknown) pIOCM.createInstance(WrongMultiply.class
						.getName(), "Multiply " + i);
			}

			pILife = (ILifeCycle) pMulIUnk.QueryInterface(ILifeCycle.class
					.getName());
			pILife.startup(pIOCM);
			runtime.connect(pMulNVIUnk, pMulIUnk, IMultiply.class.getName());
		}

		// Create the DivideNV component

		IUnknown pDivNVIUnk = (IUnknown) pIOCM.createInstance(DivideNV.class
				.getName(), "DivideNV");

		pILife = (ILifeCycle) pDivNVIUnk.QueryInterface(ILifeCycle.class
				.getName());
		pILife.startup(pIOCM);

		// Create the Divide Components

		for (int i = 1; i <= 3; i++) {
			IUnknown pDivIUnk;
			if (i == 1) {
				pDivIUnk = (IUnknown) pIOCM.createInstance(WrongDivide.class
						.getName(), "Divide " + i);
			} else {
				pDivIUnk = (IUnknown) pIOCM.createInstance(RightDivide.class
						.getName(), "Divide " + i);
			}

			pILife = (ILifeCycle) pDivIUnk.QueryInterface(ILifeCycle.class
					.getName());
			pILife.startup(pIOCM);
			runtime.connect(pDivNVIUnk, pDivIUnk, IDivide.class.getName());
		}

		// Create the SubtractContext component

		IUnknown pSubCxtIUnk = (IUnknown) pIOCM.createInstance(
				SubtractContext.class.getName(), "SubtractContext");

		pILife = (ILifeCycle) pSubCxtIUnk.QueryInterface(ILifeCycle.class
				.getName());
		pILife.startup(pIOCM);

		// Create the Subtract Components

		for (int i = 1; i <= 2; i++) {
			IUnknown pSubIUnk;
			if (i % 2 == 1) {
				pSubIUnk = (IUnknown) pIOCM.createInstance(RightSubtract.class
						.getName(), "Subtract " + i);
			} else {
				pSubIUnk = (IUnknown) pIOCM.createInstance(WrongSubtract.class
						.getName(), "Subtract " + i);
			}
			pILife = (ILifeCycle) pSubIUnk.QueryInterface(ILifeCycle.class
					.getName());
			pILife.startup(pIOCM);
			runtime.connect(pSubCxtIUnk, pSubIUnk, ISubtract.class.getName());
		}
		
		// Create the ConnectionContext component

		IUnknown pConnCxtIUnk = (IUnknown) pIOCM.createInstance(
				ConnectionState.class.getName(), "ConnectionContext");

		pILife = (ILifeCycle) pConnCxtIUnk.QueryInterface(ILifeCycle.class
				.getName());
		pILife.startup(pIOCM);

		// Create the Calculator component

		IUnknown pCalcIUnk = (IUnknown) pIOCM.createInstance(Calculator.class
				.getName(), "Calculator");
		pILife = (ILifeCycle) pCalcIUnk.QueryInterface(ILifeCycle.class
				.getName());
		pILife.startup(pIOCM);

		// Get the Calculator Interface
		ICalculator pICalc = (ICalculator) pCalcIUnk
				.QueryInterface(ICalculator.class.getName());

		// Get the SubtractContext Interface
		ContextInterface pISubtractContext = (ContextInterface) pSubCxtIUnk
				.QueryInterface(ContextInterface.class.getName());

		// Get the ISort Interface
		ISort pISort = (ISort) pSortRBIUnk
				.QueryInterface(ISort.class.getName());

		// Get the ConnectionContext Interface
		StateInterface pIConnectionContext = (StateInterface) pConnCxtIUnk
				.QueryInterface(StateInterface.class.getName());

		// Get the IConnection Interface
		IConnection pIConnection = (IConnection) pConnCxtIUnk
				.QueryInterface(IConnection.class.getName());

		runtime.connect(pCalcIUnk, pAddRBIUnk, IAdd.class.getName());
		runtime.connect(pCalcIUnk, pMulNVIUnk, IMultiply.class.getName());
		runtime.connect(pCalcIUnk, pDivNVIUnk, IDivide.class.getName());
		runtime.connect(pCalcIUnk, pSubCxtIUnk, ISubtract.class.getName());

		// Lets test the Add components
	//	System.out.println("18 + 19 = " + pICalc.add(18, 19));
	//	System.out.println("0 + 0 = " + pICalc.add(0, 0));

		// Lets test the Multiply components
	//	System.out.println("21 * 13 = " + pICalc.multiply(21, 13));
	//	System.out.println("21 * -13 = " + pICalc.multiply(21, -13));
	//	System.out.println("-21 * 13 = " + pICalc.multiply(-21, 13));
	//	System.out.println("-21 * -13 = " + pICalc.multiply(-21, -13));

		// Lets test the Divide components
	//	System.out.println("196 / 14 = " + pICalc.divide(196, 14));
	//	System.out.println("196 / -14 = " + pICalc.divide(196, -14));
	//	System.out.println("-196 / 14 = " + pICalc.divide(-196, 14));
	//	System.out.println("-196 / -14 = " + pICalc.divide(-196, -14));

		// Lets test the Subtract components
	//	pISubtractContext.changeContext(RightSubtract.class.getName());

	//	System.out.println("The value of 99 - 11 = " + pICalc.subtract(99, 11));

	//	pISubtractContext.changeContext(WrongSubtract.class.getName());

	//	System.out.println("The value of 99 - 11 = " + pICalc.subtract(99, 11));

	//	pISubtractContext.changeContext(RightSubtract.class.getName());

	//	System.out.println("The value of 99 - 11 = " + pICalc.subtract(99, 11));

		// Lets test the Sort components
		Integer[] elements = GeradorDeArranjos.geraArranjoCrescente(10);
		System.out.print("sorting ");
		GeradorDeArranjos.imprimeArranjo(elements);
		Integer[] ordered = pISort.sort(elements);
		System.out.print("ordered => ");
		GeradorDeArranjos.imprimeArranjo(ordered);

		elements = GeradorDeArranjos.geraArranjoDecrescente(10);
		System.out.print("sorting ");
		GeradorDeArranjos.imprimeArranjo(elements);
		ordered = pISort.sort(elements);
		System.out.print("ordered => ");
		GeradorDeArranjos.imprimeArranjo(ordered);

		elements = GeradorDeArranjos.geraArranjoAleatorio(10);
		System.out.print("sorting ");
		GeradorDeArranjos.imprimeArranjo(elements);
		ordered = pISort.sort(elements);
		System.out.print("ordered => ");
		GeradorDeArranjos.imprimeArranjo(ordered);



/*		File file = new File("/home/pi/inicio.txt");
                try {
                        if (file.createNewFile()) {
                                System.out.println("File named created successfully !");
                        } else {
                                System.out.println("File with name already exixts !");
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }

		int count = 0;
		while (count !=10){
		count++;
		Thread.sleep(1000);
		}

                try {
                        if (file.delete()) {
                                System.out.println("File deleted successfully !");
                        } else {
                                System.out.println("File delete operation failed !");
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }*/





		// Lets test the Connection components
		pIConnectionContext.changeState(WiFiConnection.class);
/*                File file = new File("/home/pi/inicio.txt");
                try {
                        if (file.createNewFile()) {
                                System.out.println("File named created successfully !");
                        } else {
                                System.out.println("File with name already exixts !");
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }

                int count = 0;
                while (count !=10){
                count++;
                Thread.sleep(1000);
                }

                try {
                        if (file.delete()) {
                                System.out.println("File deleted successfully !");
                        } else {
                                System.out.println("File delete operation failed !");
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }*/

		//Thread.sleep(4000);
		//long startTime = System.nanoTime();
		//pIConnection.send("bla");
		//long endTime = System.nanoTime();
		//long duration = (endTime - startTime);
		//System.out.printf("O tempo do Wifi deu: %d milisegundos \n", duration/1000000);

		//pIConnectionContext.changeState(ZigBeeConnection.class);
		//startTime = System.nanoTime();
		//pIConnection.send("bla");
		//endTime = System.nanoTime();
		//duration = (endTime - startTime);
		//System.out.printf("O tempo do Xbee: %d milisegundos \n", duration/1000000);


		//pIConnectionContext.changeState(WiFiConnection.class);
		//pIConnection.send("bla");
	}
}
