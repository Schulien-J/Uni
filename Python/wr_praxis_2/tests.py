
import numpy as np
import matplotlib.pyplot as plt
import datetime

import unittest
import tomograph
from main import compute_tomograph, gaussian_elimination, back_substitution, compute_cholesky, solve_cholesky


class Tests(unittest.TestCase):
    def test_gaussian_elimination(self):
        A = np.random.randn(4, 4)
        print()
        print(A)
        print()
        x = np.random.rand(4)
        b = np.dot(A, x)
        A_elim, b_elim = gaussian_elimination(A, b)
        print(A_elim)
        self.assertTrue(np.allclose(np.linalg.solve(A_elim, b_elim), x))  # Check if system is still solvable
        self.assertTrue(np.allclose(A_elim, np.triu(A_elim)))  # Check if matrix is upper triangular

    def test_back_substitution(self):
        A = np.array([
            [11, 44, 1],
            [0.1, 0.4, 3],
            [0, 1, -1]
        ], dtype=float)
        x_exact = np.array([-1732 / 329, 438 / 329, 109 / 329], dtype=float)
        b = np.array([1, 1, 1], dtype=float)

    # Solve using back substitution
        x_computed = back_substitution(A, b)
        print("\n")
        print(x_computed)
    # Validate the computed solution against the exact solution
        self.assertTrue(np.allclose(x_computed, x_exact, atol=1e-6))

    def test_cholesky_decomposition(self):
        # Generate a random symmetric positive definite matrix A
        n = 5  # Matrix size
        A = np.random.randn(n, n)
        A = np.dot(A, A.T)  # Make it symmetric positive semi-definite
        A += n * np.eye(n)  # Ensure it's positive definite by adding a multiple of the identity matrix

        # Perform the Cholesky decomposition using our custom implementation
        L_custom = compute_cholesky(A)

        # Perform the Cholesky decomposition using numpy.linalg
        L_numpy = np.linalg.cholesky(A)

        # Check if the custom L is lower triangular
        self.assertTrue(np.allclose(L_custom, np.tril(L_custom)), "L should be a lower triangular matrix.")

        # Check if the custom L * L^T reconstructs the original matrix A
        A_reconstructed = np.dot(L_custom, L_custom.T)
        self.assertTrue(np.allclose(A, A_reconstructed), "A should be equal to L * L^T.")

        # Check if the custom L is close to the numpy.linalg result
        self.assertTrue(np.allclose(L_custom, L_numpy), "The custom L should match the numpy.linalg result.")

        # Check that the diagonal of L contains positive values (since A is positive definite)
        self.assertTrue(np.all(np.diag(L_custom) > 0))

    def test_solve_cholesky(self):
        # Generate a random symmetric positive definite matrix A
        n = 5  # Matrix size
        A = np.random.randn(n, n)
        A = np.dot(A, A.T)  # Make it symmetric positive semi-definite
        A += n * np.eye(n)  # Ensure it's positive definite by adding a multiple of the identity matrix

        # Compute the Cholesky decomposition using numpy
        L = np.linalg.cholesky(A)

        # Generate a random right-hand side vector b
        b = np.random.randn(n)

        # Solve the system using solve_cholesky (custom implementation)
        x_custom = solve_cholesky(L, b)

        # Solve the system using numpy.linalg.solve for comparison
        # Since A = L L^T, we can solve A x = b using numpy.linalg.solve
        x_numpy = np.linalg.solve(A, b)

        # Assert that the solutions are close
        self.assertTrue(np.allclose(x_custom, x_numpy))

    def test_compute_tomograph(self):
        t = datetime.datetime.now()
        print("Start time: " + str(t.hour) + ":" + str(t.minute) + ":" + str(t.second))

        # Compute tomographic image
        n_shots =  256
        n_rays =  256
        n_grid = 128
        tim = compute_tomograph(n_shots, n_rays, n_grid)

        t = datetime.datetime.now()
        print("End time: " + str(t.hour) + ":" + str(t.minute) + ":" + str(t.second))

        # Visualize image
        plt.imshow(tim, cmap='gist_yarg', extent=[-1.0, 1.0, -1.0, 1.0],
                   origin='lower', interpolation='nearest')
        plt.gca().set_xticks([-1, 0, 1])
        plt.gca().set_yticks([-1, 0, 1])
        plt.gca().set_title('%dx%d' % (n_grid, n_grid))

        plt.show()


if __name__ == '__main__':
    unittest.main()

