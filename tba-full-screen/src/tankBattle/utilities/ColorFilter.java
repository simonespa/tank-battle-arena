/*
 * Copyright © 2008 Simone Spaccarotella
 * 
 * This file is part of "Tank Battle Arena" game.
 * 
 * Tank Battle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Tank Battle Arena is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Tank Battle.  If not, see <http://www.gnu.org/licenses/>. 
 * 
 */

package tankBattle.utilities;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

/**
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ColorFilter extends RGBImageFilter {

	/**  */
	private float[] hsl;

	/**
	 * Accetta come parametro un colore e lo scompone nelle 3 componenti
	 * primarie: RGB
	 * 
	 * @param color
	 *            un oggetto colore
	 */
	public ColorFilter(Color color) {
		hsl = fromRGBtoHSL(color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Accetta come parametri 3 interi che corrispondono ai tre colori
	 * fondamentali RGB.
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue
	 */
	public ColorFilter(int r, int g, int b) {
		hsl = fromRGBtoHSL(r, g, b);
	}

	@Override
	public int filterRGB(int r, int g, int b) {
		int l = b >> 16 & 0xff;
		int i1 = b >> 8 & 0xff;
		int j1 = b & 0xff;
		float af[] = fromRGBtoHSL(l, i1, j1);
		if (af[1] > 0.0F) {
			float f = Math.min(1.0F, (hsl[2] + af[2]) / 2.0F + hsl[2] / 7F);
			return b & 0xff000000 | fromHSLtoRGB(hsl[0], hsl[1], f);
		} else {
			return b;
		}
	}

	/**
	 * Restituisce un boolean che ci dice se due punti sono vicini.
	 * 
	 * @param d il primo punto.
	 * @param d1 il secondo punto.
	 * @return restituisce true se i due punti sono vicini.
	 */
	private boolean isNear(double d, double d1) {
		return Math.abs(d - d1) < 1.0000000000000001E-005D;
	}

	/**
	 * Trasforma il colore da RGB (Red-Green-Blue) a HSL (Hue-Saturation-Lightness).
	 *  
	 * @param r red
	 * @param g green
	 * @param b blue
	 * @return restituisce un array di floating che corrisponde alle 3 componenti HSL
	 */
	private float[] fromRGBtoHSL(int r, int g, int b) {
		float f = (float) r / 255F;
		float f1 = (float) g / 255F;
		float f2 = (float) b / 255F;
		float f3 = Math.min(Math.min(f, f1), f2);
		float f4 = Math.max(Math.max(f, f1), f2);
		float f5 = f4 - f3;
		float f6 = (f4 + f3) / 2.0F;
		float f7;
		float f8;
		if (f5 == 0.0F) {
			f7 = 0.0F;
			f8 = 0.0F;
		} else {
			if (f6 < 0.5F)
				f8 = f5 / (f4 + f3);
			else
				f8 = f5 / (2.0F - f4 - f3);
			float f9 = ((f4 - f) / 6F + f5 / 2.0F) / f5;
			float f10 = ((f4 - f1) / 6F + f5 / 2.0F) / f5;
			float f11 = ((f4 - f2) / 6F + f5 / 2.0F) / f5;
			if (isNear(f, f4))
				f7 = f11 - f10;
			else if (isNear(f1, f4))
				f7 = (0.3333333F + f9) - f11;
			else
				f7 = (0.6666667F + f10) - f9;
			if (f7 < 0.0F)
				f7++;
			if (f7 > 1.0F)
				f7--;
		}
		return (new float[] { f7, f8, f6 });
	}

	/**
	 * Trasforma il colore da HSL (Hue-Saturation-Lightness) a RGB (Red-Green-Blue).
	 * 
	 * @param f
	 * @param f1
	 * @param f2
	 * @return restituisce un intero che rappresenta il colore espresso in RGB.
	 */
	private int fromHSLtoRGB(float f, float f1, float f2) {
		float f3 = f2 > 0.5F ? (f2 + f1) - f2 * f1 : f2 * (f1 + 1.0F);
		float f4 = 2.0F * f2 - f3;
		int i = (int) (255F * fromHUEtoRGB(f4, f3, f + 0.3333333F));
		int j = (int) (255F * fromHUEtoRGB(f4, f3, f));
		int k = (int) (255F * fromHUEtoRGB(f4, f3, f - 0.3333333F));
		return (i << 8 | j) << 8 | k;
	}

	/**
	 * Effettua una trasformazione da Hue ad RGB (Red-Green-Blue).
	 * 
	 * @param f
	 * @param f1
	 * @param f2
	 * @return
	 */
	private float fromHUEtoRGB(float f, float f1, float f2) {
		if (f2 < 0.0F)
			f2++;
		if (f2 > 1.0F)
			f2--;
		if (f2 * 6F < 1.0F)
			return f + (f1 - f) * f2 * 6F;
		if (f2 * 2.0F < 1.0F)
			return f1;
		if (f2 * 3F < 2.0F)
			return f + (f1 - f) * (0.6666667F - f2) * 6F;
		else
			return f;
	}

}