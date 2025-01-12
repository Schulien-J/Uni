import numpy as np


####################################################################################################
# Exercise 1: Interpolation

def lagrange_interpolation(x: np.ndarray, y: np.ndarray) -> (np.poly1d, list):
    """
    Lagrange-Interpolation.

    Arguments:
    x : np.ndarray - x-Werte der Interpolationspunkte.
    y : np.ndarray - y-Werte der Interpolationspunkte.

    Returns:
    polynomial : np.poly1d - Das Lagrange-Interpolationspolynom.
    base_functions : list - Liste der Lagrange-Basispolynome.
    """
    n = x.size
    polynomial = np.poly1d(0)
    basisPolynome= []


    for i in range(n):

        basisPolynom = np.poly1d(1)
        for j in range(n):
            if i != j:
                basisPolynom *= np.poly1d([1, -x[j]]) / (x[i] - x[j])

        basisPolynome.append(basisPolynom)
        polynomial += y[i] * basisPolynom

    return polynomial, basisPolynome


def hermite_cubic_interpolation(x: np.ndarray, y: np.ndarray, yp: np.ndarray) -> list:
    """
    Hermite-kubische Interpolation.

    Arguments:
    x : np.ndarray - x-Werte der Interpolationspunkte.
    y : np.ndarray - y-Werte der Interpolationspunkte.
    yp : np.ndarray - Ableitungswerte an den Interpolationspunkten.

    Returns:
    spline : list - Liste von np.poly1d Objekten, je eines für jedes Intervall.
    """
    n = len(x)
    splines = []

    for i in range(n - 1):
        Bedingungen = np.array([y[i], yp[i], y[i + 1], yp[i + 1]])

        LinGleichung = np.array([
            [x[i]**3, x[i]**2, x[i], 1],          #= y[i]
            [3 * x[i]**2, 2 * x[i], 1, 0],        #= yp[i]
            [x[i + 1]**3, x[i + 1]**2, x[i + 1], 1],  #= y[i+1]
            [3 * x[i + 1]**2, 2 * x[i + 1], 1, 0]     #= yp[i+1]
     ])

        coeffs = np.linalg.solve(LinGleichung, Bedingungen)
        splines.append(np.poly1d(coeffs))

    return splines


####################################################################################################
# Exercise 2: Animation

def natural_cubic_interpolation(x: np.ndarray, y: np.ndarray) -> list:
    assert (x.size == y.size), "x and y must have the same size"
    assert np.all(np.diff(x) > 0), "x values must be strictly increasing"
    splines = []


    return splines


def periodic_cubic_interpolation(x: np.ndarray, y: np.ndarray) -> list:
    """
    Interpolate the given function with a cubic spline and periodic boundary conditions.

    Arguments:
    x: x-values of interpolation points
    y: y-values of interpolation points

    Return:
    spline: list of np.poly1d objects, each interpolating the function between two adjacent points
    """

    splines = []
    return splines


if __name__ == '__main__':

    x = np.array( [1.0, 2.0, 3.0, 4.0])
    y = np.array( [3.0, 2.0, 4.0, 1.0])

    splines = natural_cubic_interpolation( x, y)

    # # x-values to be interpolated
    # keytimes = np.linspace(0, 200, 11)
    # # y-values to be interpolated
    # keyframes = [np.array([0., -0.05, -0.2, -0.2, 0.2, -0.2, 0.25, -0.3, 0.3, 0.1, 0.2]),
    #              np.array([0., 0.0, 0.2, -0.1, -0.2, -0.1, 0.1, 0.1, 0.2, -0.3, 0.3])] * 5
    # keyframes.append(keyframes[0])
    # splines = []
    # for i in range(11):  # Iterate over all animated parts
    #     x = keytimes
    #     y = np.array([keyframes[k][i] for k in range(11)])
    #     spline = natural_cubic_interpolation(x, y)
    #     if len(spline) == 0:
    #         animate(keytimes, keyframes, linear_animation(keytimes, keyframes))
    #         self.fail("Natural cubic interpolation not implemented.")
    #     splines.append(spline)

    print("All requested functions for the assignment have to be implemented in this file and uploaded to the "
          "server for the grading.\nTo test your implemented functions you can "
          "implement/run tests in the file tests.py (> python3 -v test.py [Tests.<test_function>]).")
