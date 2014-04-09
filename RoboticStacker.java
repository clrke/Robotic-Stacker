import java.io.*;

public class RoboticStacker
{
	// BufferedReader reader for reading files
	static BufferedReader reader;
	public static void main(String[] args)
	{
		if(args.length == 0)
		{
			System.out.println("Incorrect input.\nFormat:\t\tjava RoboticStacker <input file>\nExample:\tjava RoboticStacker input.txt");
			return;
		}
		try
		{
			reader = new BufferedReader(new FileReader(new File(args[0])));
			
			String line;
			// While line is not equal to null
			while((line = reader.readLine()) != null)
			{
				// Create 6 columns, each with 20 slots (20 x 6 bin)
				int[] verticals = new int[6];
				for(int i = 0; i < verticals.length; i++)
				{
					verticals[i] = 20;
				}
				
				// Initialize number of packages to 0
				int packages = 0;
				
				// Loop until the end of the line in the file is reached or the next package cannot fit into the bin
				for(int i = 0; i < line.length(); i++)
				{
					int packageSize = Integer.parseInt(line.charAt(i)+"");
					
					boolean inserted = false;
					// Loop through each column and check if it fits into a column
					for(int j = 0; j < verticals.length; j++)
						if(verticals[j] >= packageSize)
						{
							// reduce the slots of current column by the package size
							verticals[j] -= packageSize;
							inserted = true;
							break;
						}
						
					// If inserting vertically fails, try inserting horizontally
					if(!inserted)
					{
						for(int j = 0; j < verticals.length; j++)
						{
							int slots = verticals[j];
							
							// If no slot is available, stop the current iteration and continue with the next iteration.
							if(slots <= 0) continue;
							
							// Check if it is okay to insert horizontally
							boolean okayForHorizontal = true;
							for(int k = 1; k < packageSize; k++)
							{
								if((j+k) >= verticals.length || verticals[j+k] != slots)
								{
									okayForHorizontal = false;
									break;
								}
							}
							
							// If okay, remove a slot from each affected column
							if(okayForHorizontal)
							{
								for(int k = 0; k < packageSize; k++)
								{
									verticals[j+k] -= 1;
								}
								inserted = true;
								break;
							}
						}
					}
					
					// If inserted, increment number of packages
					if(inserted)
						packages++;
					else break;
				}
				
				// Count free spaces
				int freeSpace = 0;
				for(int i = 0; i < verticals.length; i++)
					freeSpace += verticals[i];
					
				// Output number of packages and free spaces
				System.out.println(String.format("%d %d", packages, freeSpace));
			}
		}
		catch(Exception error)
		{
			System.out.println("Invalid input.");
		}
	}
}