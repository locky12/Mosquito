package moskitoAttack;

import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 

public class MersenneTwister extends java.util.Random {

	private static final long serialVersionUID = 1L; 

	// Period parameters 
	private static final int N = 624; 

	private static final int M = 397; 

	private static final int MATRIX_A = 0x9908b0df; 

	// private static final * constant vector a 
	private static final int UPPER_MASK = 0x80000000; 

	// most significant w-r bits 
	private static final int LOWER_MASK = 0x7fffffff; 

	// least significant r bits 

	// Tempering parameters 
	private static final int TEMPERING_MASK_B = 0x9d2c5680; 

	private static final int TEMPERING_MASK_C = 0xefc60000; 

	// #define TEMPERING_SHIFT_U(y) (y >>> 11) 
	// #define TEMPERING_SHIFT_S(y) (y << 7) 
	// #define TEMPERING_SHIFT_T(y) (y << 15) 
	// #define TEMPERING_SHIFT_L(y) (y >>> 18) 

	private int mt[]; // the array for the state vector 

	private int mti; // mti==N+1 means mt[N] is not initialized 

	private int mag01[]; 

	// a good initial seed (of int size, though stored in a long) 
	private static final long GOOD_SEED = 4357; 

	/*
	 * implemented here because there's a bug in Random's implementation of the 
	 * Gaussian code (divide by zero, and log(0), ugh!), yet its gaussian 
	 * variables are private so we can't access them here. :-( 
	 */ 

	private double __nextNextGaussian; 

	private boolean __haveNextNextGaussian; 

	/**
	 * Constructor using the default seed. 
	 */ 
	public MersenneTwister() 
	{ 
		super(GOOD_SEED); /* just in case */ 
		this.setSeed(GOOD_SEED); 
	} 

	/**
	 * Constructor using a given seed. Though you pass this seed in as a long, 
	 * it's best to make sure it's actually an integer. 
	 *  
	 * @param seed the seed to use 
	 */ 
	public MersenneTwister(final long seed) 
	{ 
		super(seed); /* just in case */ 
		this.setSeed(seed); 
	} 

	/**
	 * @param bits the number of bits to use 
	 * @return an integer with <i>bits</i> bits filled with a random number 
	 */ 
	@Override 
	synchronized protected int next(final int bits) 
	{ 
		int y; 

		if (this.mti >= N) // generate N words at one time 
		{ 
			int kk; 

			for (kk = 0; kk < N - M; kk++) 
			{ 
				y = this.mt[kk] & UPPER_MASK | this.mt[kk + 1] & LOWER_MASK; 
				this.mt[kk] = this.mt[kk + M] ^ y >>> 1 ^ this.mag01[y & 0x1]; 
			} 
			for (; kk < N - 1; kk++) 
			{ 
				y = this.mt[kk] & UPPER_MASK | this.mt[kk + 1] & LOWER_MASK; 
				this.mt[kk] = this.mt[kk + M - N] ^ y >>> 1 ^ this.mag01[y & 0x1]; 
			} 
			y = this.mt[N - 1] & UPPER_MASK | this.mt[0] & LOWER_MASK; 
			this.mt[N - 1] = this.mt[M - 1] ^ y >>> 1 ^ this.mag01[y & 0x1]; 

			this.mti = 0; 
		} 

		y = this.mt[this.mti++]; 
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y) 
		y ^= y << 7 & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y) 
		y ^= y << 15 & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y) 
		y ^= y >>> 18; // TEMPERING_SHIFT_L(y) 

		return y >>> 32 - bits; // hope that's right! 
	} 

	/**
	 * This generates a coin flip with a probability <tt>probability</tt> of 
	 * returning true, else returning false. <tt>probability</tt> must be 
	 * between 0.0 and 1.0, inclusive. 
	 *  
	 * @param probability must be between 0.0 and 1.0 
	 * @return the result of the coin flip 
	 */ 

	public boolean nextBoolean(final double probability) 
	{ 
		if (probability < 0.0 || probability > 1.0) { 
			throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive."); 
		} 
		return this.nextDouble() < probability; 
	} 

	/**
	 * This generates a coin flip with a probability <tt>probability</tt> of 
	 * returning true, else returning false. <tt>probability</tt> must be 
	 * between 0.0 and 1.0, inclusive. Not as precise a random real event as 
	 * nextBoolean(double), but twice as fast. To explicitly use this, remember 
	 * you may need to cast to float first. 
	 *  
	 * @param probability the probability to use (between 0.0 and 1.0) 
	 * @return the coin flip result 
	 */ 

	public boolean nextBoolean(final float probability) 
	{ 
		if (probability < 0.0f || probability > 1.0f) { 
			throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive."); 
		} 
		return this.nextFloat() < probability; 
	} 

	/**
	 * For completeness' sake, though it's not in java.util.Random. 
	 *  
	 * @return the next byte 
	 */ 

	public byte nextByte() 
	{ 
		return (byte) this.next(8); 
	} 

	/*
	 * If you've got a truly old version of Java, you can omit these two next 
	 * methods. 
	 */ 

	/**
	 * A bug fix for all versions of the JDK. The JDK appears to use all four 
	 * bytes in an integer as independent byte values! Totally wrong. I've 
	 * submitted a bug report. 
	 *  
	 * @param bytes the bytes for which to get the next bytes (?) 
	 */ 
	@Override 
	public void nextBytes(final byte[] bytes) 
	{ 
		for (int x = 0; x < bytes.length; x++) { 
			bytes[x] = (byte) this.next(8); 
		} 
	} 

	/**
	 * For completeness' sake, though it's not in java.util.Random. 
	 *  
	 * @return the next char 
	 */ 
	public char nextChar() 
	{ 
		// chars are 16-bit UniCode values 
		return (char) this.next(16); 
	} 

	/**
	 * A bug fix for all JDK code including 1.2. nextGaussian can theoretically 
	 * ask for the log of 0 and divide it by 0! See Java bug <a 
	 * href="http://developer.java.sun.com/developer/bugParade/bugs/4254501.html"> 
	 * http://developer.java.sun.com/developer/bugParade/bugs/4254501.html </a> 
	 *  
	 * @return the next Gaussian 
	 */ 

	@Override 
	synchronized public double nextGaussian() 
	{ 
		if (this.__haveNextNextGaussian) 
		{ 
			this.__haveNextNextGaussian = false; 
			return this.__nextNextGaussian; 
		} 
		// (otherwise...) 
		double v1, v2, s; 
		do 
		{ 
			v1 = 2 * this.nextDouble() - 1; // between -1.0 and 1.0 
			v2 = 2 * this.nextDouble() - 1; // between -1.0 and 1.0 
			s = v1 * v1 + v2 * v2; 
		}while (s >= 1 || s == 0); 
		double multiplier = Math.sqrt(-2 * Math.log(s) / s); 
		this.__nextNextGaussian = v2 * multiplier; 
		this.__haveNextNextGaussian = true; 
		return v1 * multiplier; 
	} 

	/**
	 * For completeness' sake, though it's not in java.util.Random. 
	 *  
	 * @return the next short 
	 */ 

	public short nextShort() 
	{ 
		return (short) this.next(16); 
	} 

	@SuppressWarnings("unused") 
	private synchronized static void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException 
	{ 
		// just so we're synchronized. 
		in.defaultReadObject(); 
	} 

	/**
	 * An alternative, more complete, method of seeding the pseudo random number 
	 * generator. array must be an array of 624 ints, and they can be any value 
	 * as long as they're not *all* zero. 
	 *  
	 * @param array an array of 624 ints 
	 */ 

	synchronized public void setSeed(final int[] array) 
	{ 
		// it's always good style to call super -- 
		// we'll use MT's canonical random number, but it doesn't 
		// really matter. 
		super.setSeed(4357); 

		// Due to a bug in java.util.Random clear up to 1.2, we're 
		// doing our own Gaussian variable. 
		this.__haveNextNextGaussian = false; 

		this.mt = new int[N]; 
		System.arraycopy(array, 0, this.mt, 0, N); 
		this.mti = N; 
		// mag01[x] = x * MATRIX_A for x=0,1 
		this.mag01 = new int[2]; 
		this.mag01[0] = 0x0; 
		this.mag01[1] = MATRIX_A; 
	} 

	/**
	 * Initalize the pseudo random number generator. Don't pass in a long that's 
	 * bigger than an int (Mersenne Twister only uses the first 32 bits for its 
	 * seed). 
	 *  
	 * @param seed the seed to use 
	 */ 
	@Override 
	synchronized public void setSeed(final long seed) 
	{ 
		// it's always good style to call super 
		super.setSeed(seed); 

		// seed needs to be casted into an int first for this to work 
		int _seed = (int) seed; 

		// Due to a bug in java.util.Random clear up to 1.2, we're 
		// doing our own Gaussian variable. 
		this.__haveNextNextGaussian = false; 

		this.mt = new int[N]; 

		for (int i = 0; i < N; i++) 
		{ 
			this.mt[i] = _seed & 0xffff0000; 
			_seed = 69069 * _seed + 1; 
			this.mt[i] |= (_seed & 0xffff0000) >>> 16; 
		_seed = 69069 * _seed + 1; 
		} 

		this.mti = N; 
		// mag01[x] = x * MATRIX_A for x=0,1 
		this.mag01 = new int[2]; 
		this.mag01[0] = 0x0; 
		this.mag01[1] = MATRIX_A; 
	} 

	/**
	 * Initalize the pseudo random number generator. This is the old 
	 * seed-setting mechanism for the original Mersenne Twister algorithm. You 
	 * must not use 0 as your seed, and don't pass in a long that's bigger than 
	 * an int (Mersenne Twister only uses the first 32 bits for its seed). Also 
	 * it's suggested that for you avoid even-numbered seeds in this older 
	 * seed-generation procedure. 
	 *  
	 * @param seed the seed to use 
	 */ 

	synchronized public void setSeedOld(final long seed) 
	{ 
		// it's always good style to call super 
		super.setSeed(seed); 

		// Due to a bug in java.util.Random clear up to 1.2, we're 
		// doing our own Gaussian variable. 
		this.__haveNextNextGaussian = false; 

		this.mt = new int[N]; 

		// setting initial seeds to mt[N] using 
		// the generator Line 25 of Table 1 in 
		// [KNUTH 1981, The Art of Computer Programming 
		// Vol. 2 (2nd Ed.), pp102] 

		// the 0xffffffff is commented out because in Java 
		// ints are always 32 bits; hence i & 0xffffffff == i 

		this.mt[0] = (int) seed; // & 0xffffffff; 

		for (this.mti = 1; this.mti < N; this.mti++) 
		{ 
			this.mt[this.mti] = 69069 * this.mt[this.mti - 1]; // & 
			// 0xffffffff; 
		} 

		// mag01[x] = x * MATRIX_A for x=0,1 
		this.mag01 = new int[2]; 
		this.mag01[0] = 0x0; 
		this.mag01[1] = MATRIX_A; 
	} 

	@SuppressWarnings("static-method") 
	private synchronized void writeObject(final ObjectOutputStream out) throws IOException 
	{ 
		// just so we're synchronized. 
		out.defaultWriteObject(); 
	} 
}

