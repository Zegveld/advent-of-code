package eu.helral.advent.of.code.y2015;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day10 extends DayTemplate {

	/**
	 * https://adventofcode.com/2015/day/10
	 *
	 * <pre>
	 * --- Day 10: Elves Look, Elves Say ---
	 * Today, the Elves are playing a game called look-and-say. They take turns making sequences by reading aloud
	 * the previous sequence and using that reading as the next sequence. For example, 211 is read as "one two, two ones",
	 * which becomes 1221 (1 2, 2 1s).
	 *
	 * Look-and-say sequences are generated iteratively, using the previous value as input for the next step. For each step,
	 * take the previous value, and replace each run of digits (like 111) with the number of digits (3) followed by the digit itself (1).
	 *
	 * For example:
	 *
	 * 1 becomes 11 (1 copy of digit 1).
	 * 11 becomes 21 (2 copies of digit 1).
	 * 21 becomes 1211 (one 2 followed by one 1).
	 * 1211 becomes 111221 (one 1, one 2, and two 1s).
	 * 111221 becomes 312211 (three 1s, two 2s, and one 1).
	 * Starting with the digits in your puzzle input, apply this process 40 times. What is the length of the result?
	 *
	 * Your puzzle input is 1113122113.
	 * </pre>
	 */
	public int part1() {
		return repeatNumberChaining(FR, 40).stream().mapToInt(ConwaySequence::getSize).sum();
	}

	class ConwaySequence {
		private final String current;
		private final List<ConwaySequence> next = new ArrayList<>();

		public ConwaySequence(String current) {
			this.current = current;
		}

		void addNext(ConwaySequence next) {
			this.next.add(next);
		}

		int getSize() {
			return current.length();
		}

		public List<ConwaySequence> getNext() {
			return next;
		}
	}

	ConwaySequence HE = new ConwaySequence("13112221133211322112211213322112"); // 11132132212312211322212221121123222112
	ConwaySequence BE = new ConwaySequence("111312211312113221133211322112211213322112"); // 3113112221131112211322212312211322212221121123222112
	ConwaySequence MG = new ConwaySequence("3113322112"); // 132123222112
	ConwaySequence P = new ConwaySequence("311311222112"); // 13211321322112
	ConwaySequence SC = new ConwaySequence("3113112221133112"); // 132113213221232112
	ConwaySequence MN = new ConwaySequence("111311222112"); // 311321322112
	ConwaySequence NI = new ConwaySequence("11133112"); // 31232112
	ConwaySequence GA = new ConwaySequence("13221133122211332"); // 11132221231132212312
	ConwaySequence GE = new ConwaySequence("31131122211311122113222"); // 132113213221133122211332
	ConwaySequence AS = new ConwaySequence("11131221131211322113322112"); // 31131122211311122113222123222112
	ConwaySequence Y = new ConwaySequence("1112133"); // 31121123
	ConwaySequence ZR = new ConwaySequence("12322211331222113112211"); // 11121332212311322113212221
	ConwaySequence NB = new ConwaySequence("1113122113322113111221131221"); // 31131122212322211331222113112211
	ConwaySequence RU = new ConwaySequence("132211331222113112211"); // 111322212311322113212221
	ConwaySequence RH = new ConwaySequence("311311222113111221131221"); // 1321132132211331222113112211
	ConwaySequence SB = new ConwaySequence("3112221"); // 13213211
	ConwaySequence TE = new ConwaySequence("1322113312211"); // 1113222123112221
	ConwaySequence I = new ConwaySequence("311311222113111221"); // 13211321322113312211
	ConwaySequence CE = new ConwaySequence("1321133112"); // 11131221232112
	ConwaySequence SM = new ConwaySequence("311332"); // 13212312
	ConwaySequence GD = new ConwaySequence("13221133112"); // 11132221232112
	ConwaySequence TB = new ConwaySequence("3113112221131112"); // 132113213221133112
	ConwaySequence ER = new ConwaySequence("311311222"); // 1321132132
	ConwaySequence TM = new ConwaySequence("11131221133112"); // 3113112221232112
	ConwaySequence TA = new ConwaySequence("13112221133211322112211213322113"); // 11132132212312211322212221121123222113
	ConwaySequence RE = new ConwaySequence("111312211312113221133211322112211213322113"); // 3113112221131112211322212312211322212221121123222113
	ConwaySequence BI = new ConwaySequence("3113322113"); // 132123222113
	ConwaySequence H = new ConwaySequence("22"); // 22
	ConwaySequence B = new ConwaySequence("1321132122211322212221121123222112"); // 111312211312113221133211322112211213322112
	ConwaySequence N = new ConwaySequence("111312212221121123222112"); // 3113112211322112211213322112
	ConwaySequence F = new ConwaySequence("31121123222112"); // 132112211213322112
	ConwaySequence NE = new ConwaySequence("111213322112"); // 31121123222112
	ConwaySequence NA = new ConwaySequence("123222112"); // 111213322112
	ConwaySequence AL = new ConwaySequence("1113222112"); // 3113322112
	ConwaySequence SI = new ConwaySequence("1322112"); // 1113222112
	ConwaySequence S = new ConwaySequence("1113122112"); // 311311222112
	ConwaySequence CL = new ConwaySequence("132112"); // 1113122112
	ConwaySequence AR = new ConwaySequence("3112"); // 132112
	ConwaySequence K = new ConwaySequence("1112"); // 3112
	ConwaySequence CA = new ConwaySequence("12"); // 1112
	ConwaySequence TI = new ConwaySequence("11131221131112"); // 3113112221133112
	ConwaySequence V = new ConwaySequence("13211312"); // 11131221131112
	ConwaySequence CR = new ConwaySequence("31132"); // 13211312
	ConwaySequence FE = new ConwaySequence("13122112"); // 111311222112
	ConwaySequence CO = new ConwaySequence("32112"); // 13122112
	ConwaySequence CU = new ConwaySequence("131112"); // 11133112
	ConwaySequence ZN = new ConwaySequence("312"); // 131112
	ConwaySequence SE = new ConwaySequence("13211321222113222112"); // 11131221131211322113322112
	ConwaySequence BR = new ConwaySequence("3113112211322112"); // 13211321222113222112
	ConwaySequence KR = new ConwaySequence("11131221222112"); // 3113112211322112
	ConwaySequence RB = new ConwaySequence("1321122112"); // 11131221222112
	ConwaySequence SR = new ConwaySequence("3112112"); // 1321122112
	ConwaySequence MO = new ConwaySequence("13211322211312113211"); // 1113122113322113111221131221
	ConwaySequence TC = new ConwaySequence("311322113212221"); // 13211322211312113211
	ConwaySequence PD = new ConwaySequence("111312211312113211"); // 311311222113111221131221
	ConwaySequence AG = new ConwaySequence("132113212221"); // 111312211312113211
	ConwaySequence CD = new ConwaySequence("3113112211"); // 132113212221
	ConwaySequence IN = new ConwaySequence("11131221"); // 3113112211
	ConwaySequence SN = new ConwaySequence("13211"); // 11131221
	ConwaySequence XE = new ConwaySequence("11131221131211"); // 311311222113111221
	ConwaySequence CS = new ConwaySequence("13211321"); // 11131221131211
	ConwaySequence BA = new ConwaySequence("311311"); // 13211321
	ConwaySequence LA = new ConwaySequence("11131"); // 311311
	ConwaySequence PR = new ConwaySequence("31131112"); // 1321133112
	ConwaySequence ND = new ConwaySequence("111312"); // 31131112
	ConwaySequence PM = new ConwaySequence("132"); // 111312
	ConwaySequence EU = new ConwaySequence("1113222"); // 311332
	ConwaySequence DY = new ConwaySequence("111312211312"); // 3113112221131112
	ConwaySequence HO = new ConwaySequence("1321132"); // 111312211312
	ConwaySequence YB = new ConwaySequence("1321131112"); // 11131221133112
	ConwaySequence LU = new ConwaySequence("311312"); // 1321131112
	ConwaySequence HF = new ConwaySequence("11132");// 311312
	ConwaySequence W = new ConwaySequence("312211322212221121123222113"); // 13112221133211322112211213322113
	ConwaySequence OS = new ConwaySequence("1321132122211322212221121123222113"); // 111312211312113221133211322112211213322113
	ConwaySequence IR = new ConwaySequence("3113112211322112211213322113"); // 1321132122211322212221121123222113
	ConwaySequence PT = new ConwaySequence("111312212221121123222113"); // 3113112211322112211213322113
	ConwaySequence AU = new ConwaySequence("132112211213322113"); // 111312212221121123222113
	ConwaySequence HG = new ConwaySequence("31121123222113"); // 132112211213322113
	ConwaySequence TL = new ConwaySequence("111213322113"); // 31121123222113
	ConwaySequence PB = new ConwaySequence("123222113"); // 111213322113
	ConwaySequence PO = new ConwaySequence("1113222113"); // 3113322113
	ConwaySequence AT = new ConwaySequence("1322113"); // 1113222113
	ConwaySequence FR = new ConwaySequence("1113122113"); // 311311222113
	ConwaySequence RA = new ConwaySequence("132113"); // 1113122113
	ConwaySequence AC = new ConwaySequence("3113"); // 132113
	ConwaySequence TH = new ConwaySequence("1113"); // 3113
	ConwaySequence PA = new ConwaySequence("13"); // 1113
	ConwaySequence U = new ConwaySequence("3"); // 13
	ConwaySequence RN = new ConwaySequence("311311222113"); // 1321132 - 1322113
	ConwaySequence LI = new ConwaySequence("312211322212221121123222112"); // 13112221133211322112211213322112
	ConwaySequence C = new ConwaySequence("3113112211322112211213322112"); // 1321132122211322212221121123222112
	ConwaySequence O = new ConwaySequence("132112211213322112"); // 111312212221121123222112
	{
		F.addNext(O);
		NE.addNext(F);
		NA.addNext(NE);
		AL.addNext(MG);
		SI.addNext(AL);
		S.addNext(P);
		CL.addNext(S);
		AR.addNext(CL);
		K.addNext(AR);
		CA.addNext(K);
		TI.addNext(SC);
		V.addNext(TI);
		CR.addNext(V);
		FE.addNext(MN);
		CO.addNext(FE);
		CU.addNext(NI);
		ZN.addNext(CU);
		SE.addNext(AS);
		BR.addNext(SE);
		KR.addNext(BR);
		RB.addNext(KR);
		SR.addNext(RB);
		MO.addNext(NB);
		TC.addNext(MO);
		PD.addNext(RH);
		AG.addNext(PD);
		CD.addNext(AG);
		IN.addNext(CD);
		SN.addNext(IN);
		XE.addNext(I);
		CS.addNext(XE);
		BA.addNext(CS);
		LA.addNext(BA);
		PR.addNext(CE);
		ND.addNext(PR);
		PM.addNext(ND);
		EU.addNext(SM);
		DY.addNext(TB);
		HO.addNext(DY);
		YB.addNext(TM);
		LU.addNext(YB);
		HF.addNext(LU);
		W.addNext(TA);
		OS.addNext(RE);
		IR.addNext(OS);
		PT.addNext(IR);
		AU.addNext(PT);
		HG.addNext(AU);
		TL.addNext(HG);
		PB.addNext(TL);
		PO.addNext(BI);
		AT.addNext(PO);
		FR.addNext(RN);
		RA.addNext(FR);
		AC.addNext(RA);
		TH.addNext(AC);
		PA.addNext(TH);
		U.addNext(PA);

		H.addNext(H);
		LI.addNext(HE);
		B.addNext(BE);
		C.addNext(B);
		N.addNext(C);
		O.addNext(N);
		// HE = // "11132" HF - "13" PA - "22" H - "12" CA -
		// "312211322212221121123222112" LI
		HE.addNext(HF);
		HE.addNext(PA);
		HE.addNext(H);
		HE.addNext(CA);
		HE.addNext(LI);
		// BE = // "31131122211311122113222" GE - "12" CA -
		// "312211322212221121123222112" LI
		BE.addNext(GE);
		BE.addNext(CA);
		BE.addNext(LI);
		// MG = // "132" PM - "123222112" NA
		MG.addNext(PM);
		MG.addNext(NA);
		// P = // "1321132" HO - "1322112" SI
		P.addNext(HO);
		P.addNext(SI);
		// SC = // "1321132" HO - "13" PA - "22" H - "12" CA - "32112" CO
		SC.addNext(HO);
		SC.addNext(PA);
		SC.addNext(H);
		SC.addNext(CA);
		SC.addNext(CO);
		// MN = // "31132" CR - "1322112" SI
		MN.addNext(CR);
		MN.addNext(SI);
		// NI = // "312" ZN - "32112" CO
		NI.addNext(ZN);
		NI.addNext(CO);
		// GA = // "1113222" EU - "12" CA - "3113" AC - "22" H - "12" CA - "312" ZN
		GA.addNext(EU);
		GA.addNext(CA);
		GA.addNext(AC);
		GA.addNext(H);
		GA.addNext(CA);
		GA.addNext(ZN);
		// GE = // "1321132" HO - "13221133122211332" GA
		GE.addNext(HO);
		GE.addNext(GA);
		// AS = // "31131122211311122113222" GE - "123222112" NA
		AS.addNext(GE);
		AS.addNext(NA);
		// Y = // "3112112" SR - "3" U
		Y.addNext(SR);
		Y.addNext(U);
		// ZR = // "1112133" Y - "22" H - "12" CA - "311322113212221" TC
		ZR.addNext(Y);
		ZR.addNext(H);
		ZR.addNext(CA);
		ZR.addNext(TC);
		// NB = // "311311222" ER - "12322211331222113112211" ZR
		NB.addNext(ER);
		NB.addNext(ZR);
		// RU = // "1113222" EU - "12" CA - "311322113212221" TC
		RU.addNext(EU);
		RU.addNext(CA);
		RU.addNext(TC);
		// RH = // "1321132" HO - "132211331222113112211" RU
		RH.addNext(HO);
		RH.addNext(RU);
		// SB = // "132" PM - "13211" SN
		SB.addNext(PM);
		SB.addNext(SN);
		// TE = // "1113222" EU - "12" CA - "3112221" SB
		TE.addNext(EU);
		TE.addNext(CA);
		TE.addNext(SB);
		// I = // "1321132" HO - "1322113312211" TE
		I.addNext(HO);
		I.addNext(TE);
		// CE = // "11131" LA - "22" H - "12" CA - "32112" CO
		CE.addNext(LA);
		CE.addNext(H);
		CE.addNext(CA);
		CE.addNext(CO);
		// SM = // "132" PM - "12" CA - "312" ZN
		SM.addNext(PM);
		SM.addNext(CA);
		SM.addNext(ZN);
		// GD = // "1113222" EU - "12" CA - "32112" CO
		GD.addNext(EU);
		GD.addNext(CA);
		GD.addNext(CO);
		// TB = // "1321132" HO - "13221133112" GD
		TB.addNext(HO);
		TB.addNext(GD);
		// ER = // "1321132" HO - "132" PM
		ER.addNext(HO);
		ER.addNext(PM);
		// TM = // "311311222" ER - "12" CA - "32112" CO
		TM.addNext(ER);
		TM.addNext(CA);
		TM.addNext(CO);
		// TA = // "11132" HF - "13" PA - "22" H - "12" CA -
		// "312211322212221121123222113" W
		TA.addNext(HF);
		TA.addNext(PA);
		TA.addNext(H);
		TA.addNext(CA);
		TA.addNext(W);
		// RE = // "31131122211311122113222" GE - "12" CA -
		// "312211322212221121123222113" W
		RE.addNext(GE);
		RE.addNext(CA);
		RE.addNext(W);
		// BI = // "132" PM - "123222113" PB
		BI.addNext(PM);
		BI.addNext(PB);
		// RN = // "1321132" HO - "1322113" AT
		RN.addNext(HO);
		RN.addNext(AT);
	}

	/**
	 * first attempt, took long enough to finish to write out the ConwaySequence's
	 * and then determine which turn into which.
	 */
	@Deprecated
	private String repeatNumberChaining(String input, int repeat) {
		for (int i = 0; i < repeat; i++) {
			String next = "";
			for (int j = 0; j < input.length();) {
				char currentChar = input.charAt(j);
				j++;
				int counter = 1;
				while (j < input.length() && input.charAt(j) == currentChar) {
					counter++;
					j++;
				}
				next += "" + counter + "" + currentChar;
			}
			System.out.println(i);
			System.out.println(next);
			input = next;
		}
		return input;
	}

	private List<ConwaySequence> repeatNumberChaining(ConwaySequence initial, int repeat) {
		List<ConwaySequence> result = List.of(initial);
		for (int i = 0; i < repeat; i++) {
			result = result.stream().map(ConwaySequence::getNext).flatMap(Collection::stream).toList();
		}
		return result;
	}

	/**
	 * <pre>
	 * Neat, right? You might also enjoy hearing John Conway talking about this sequence (that's Conway of Conway's Game of Life fame).
	 *
	 * Now, starting again with the digits in your puzzle input, apply this process 50 times. What is the length of the new result?
	 *
	 * </pre>
	 */
	public int part2() {
		return repeatNumberChaining(FR, 50).stream().mapToInt(ConwaySequence::getSize).sum();
	}

}
