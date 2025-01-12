
import numpy as np
import tomograph


####################################################################################################
# Exercise 1: Gaussian elimination

def gaussian_elimination(A: np.ndarray, b: np.ndarray, use_pivoting: bool = True) -> (np.ndarray, np.ndarray):
    """
    Gaussian Elimination of Ax=b with or without pivoting.

    Arguments:
    A : matrix, representing left side of equation system of size: (m,m)
    b : vector, representing right hand side of size: (m, )
    use_pivoting : flag if pivoting should be used

    Return:
    A : reduced result matrix in row echelon form (type: np.ndarray, size: (m,m))
    b : result vector in row echelon form (type: np.ndarray, size: (m, ))

    Raised Exceptions:
    ValueError: if matrix and vector sizes are incompatible, matrix is not square or pivoting is disabled but necessary

    Forbidden:
    - numpy.linalg.*
    """
    if A.shape[0] != A.shape[1]:
        raise ValueError("invalid Matrix")
    if b.shape[0] != A.shape[0]:
        raise ValueError("b is incompatible")

    A = A.copy()
    b = b.copy()


    for i in range(A.shape[0]):

        if use_pivoting:
            max_row = i + np.argmax(abs(A[i:A.shape[0], i]))
            if A[max_row, i] == 0:
                raise ValueError("Pivoting needed")
            A[[i, max_row]] = A[[max_row, i]]
            b[[i, max_row]] = b[[max_row, i]]

        for j in range(i + 1, A.shape[0]):
            if A[j, i] != 0:
                if A[i, i] == 0:
                    raise ValueError("Pivoting needed")
                factor = A[j, i] / A[i, i]
                for k in range(i, A.shape[0]):
                    A[j, k] -= factor * A[i, k]

                b[j] -= factor * b[i]

    return A, b


def back_substitution(A: np.ndarray, b: np.ndarray) -> np.ndarray:

    if A.shape[0] != A.shape[1]:
        raise ValueError("invalid Matrix")
    if b.shape[0] != A.shape[0]:
        raise ValueError("b is incompatible")
    if not np.allclose(A, np.triu(A)):
        A, b = gaussian_elimination(A, b)
    for i in range(A.shape[0]):
        if A[i, i] == 0:
            raise ValueError("no unique solution")

    x = np.zeros_like(b, dtype=float)
    for i in range(len(b)-1, -1, -1):
        sum_terms = 0
        for j in range(i + 1, len(b)):
            sum_terms += A[i, j] * x[j]

        x[i] =(b[i] - sum_terms)/A[i, i]

    return x


####################################################################################################
# Exercise 2: Cholesky decomposition

def compute_cholesky(M: np.ndarray) -> np.ndarray:
    """
    Compute Cholesky decomposition of a matrix

    Arguments:
    M : matrix, symmetric and positive (semi-)definite

    Raised Exceptions:
    ValueError: L is not symmetric and psd

    Return:
    L :  Cholesky factor of M

    Forbidden:
    - numpy.linalg.*

    """

    if (M.shape[0] != M.shape[1]):
     raise ValueError("Matrix is not symmetric.")

    L = np.zeros((M.shape[0], M.shape[0]))
    for i in range(M.shape[0]):
        temp = M[i,i]
        for k in range(i):
            temp -= L[i,k]**2

        if temp <= 0:
            raise ValueError("Matrix invalid")

        L[i, i] = np.sqrt(temp)
        for j in range(i+1, M.shape[0]):
            temp = M[i,j]
            for k in range(i):
                temp -= L[i,k] * L[j,k]

            L[j, i] = temp / L[i, i]

    return L


def solve_cholesky(L: np.ndarray, b: np.ndarray) -> np.ndarray:
    """
    Solve the system L L^T x = b where L is a lower triangular matrix

    Arguments:
    L : matrix representing the Cholesky factor
    b : right hand side of the linear system

    Raised Exceptions:
    ValueError: sizes of L, b do not match
    ValueError: L is not lower triangular matrix

    Return:
    x : solution of the linear system

    Forbidden:
    - numpy.linalg.*
    """
    if L.shape[0] != L.shape[1]:
        raise ValueError("Matrix L not square.")


    if not np.allclose(L, np.tril(L)):
        raise ValueError("Matrix L invalid.")

    y = np.zeros_like(b, dtype=float)
    for i in range(L.shape[0]):
        temp_sum = 0.0
        for j in range(i):
            temp_sum += L[i, j] * y[j]
        y[i] = (b[i] - temp_sum) / L[i, i]

    x = np.zeros_like(b, dtype=float)
    for i in range(L.shape[0]-1, -1, -1):
        temp_sum = 0.0
        for j in range(i+1, L.shape[0]):
            temp_sum += L[j, i] * x[j]
        x[i] = (y[i] - temp_sum) / L[i, i]

    return x


####################################################################################################
# Exercise 3: Tomography

def setup_system_tomograph(n_shots: np.int64, n_rays: np.int64, n_grid: np.int64) -> (np.ndarray, np.ndarray):
    """
    Set up the linear system describing the tomographic reconstruction

    Arguments:
    n_shots : number of different shot directions
    n_rays  : number of parallel rays per direction
    n_grid  : number of cells of grid in each direction, in total n_grid*n_grid cells

    Return:
    L : system matrix
    g : measured intensities

    Raised Exceptions:
    -

    Side Effects:
    -

    Forbidden:
    -
    """

    # TODO: Initialize system matrix with proper size
    L = np.zeros((n_shots*n_rays, n_grid*n_grid))
    # TODO: Initialize intensity vector
    g = np.zeros(n_shots*n_rays)

    # TODO: Iterate over equispaced angles, take measurements, and update system matrix and sinogram
    theta = 0
    offset = 0
    a = 0
    # Take a measurement with the tomograph from direction r_theta.
    # intensities: measured intensities for all <n_rays> rays of the measurement. intensities[n] contains the intensity for the n-th ray
    # ray_indices: indices of rays that intersect a cell
    # isect_indices: indices of intersected cells
    # lengths: lengths of segments in intersected cells
    # The tuple (ray_indices[n], isect_indices[n], lengths[n]) stores which ray has intersected which cell with which length. n runs from 0 to the amount of ray/cell intersections (-1) of this measurement.

    for a in range(n_shots):

        theta = (np.pi / n_shots) * a
        intensities, ray_indices, isect_indices, lengths = tomograph.take_measurement(n_grid, n_rays, theta)

        for i in range(n_rays):
            g[offset + i] = intensities[i]
        for n in range(ray_indices.shape[0]):
            L[ray_indices[n]+offset][isect_indices[n]] =  lengths[n]
        offset += n_rays
    print(L)
    return [L, g]


def compute_tomograph(n_shots: np.int64, n_rays: np.int64, n_grid: np.int64) -> np.ndarray:
    """
    Compute tomographic image

    Arguments:
    n_shots : number of different shot directions
    n_rays  : number of parallel rays per direction
    n_grid  : number of cells of grid in each direction, in total n_grid*n_grid cells

    Return:
    tim : tomographic image

    Raised Exceptions:
    -
    """


    [L, g] = setup_system_tomograph(n_shots, n_rays, n_grid)

    LT = L.T
    A = np.dot(LT, L)
    b = np.dot(LT, g)

    epsilon = np.finfo(float).eps
    A += epsilon * 2

    Chol = np.linalg.cholesky(A)
    y = np.linalg.solve(Chol, b)
    x = np.linalg.solve(Chol.T, y)
    tim = x.reshape((n_grid, n_grid))

    return tim


if __name__ == '__main__':
    print("All requested functions for the assignment have to be implemented in this file and uploaded to the "
          "server for the grading.\nTo test your implemented functions you can "
          "implement/run tests in the file tests.py (> python3 -v test.py [Tests.<test_function>]).")
