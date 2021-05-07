/**
 * This is a minimal implementation of 0/1 Knapsack problem. It's done in JavaScript so you can just
 * copy-n-past it into any JS console to see it execute. Here is the YouTube video that explains the
 * the algorithm: https://youtu.be/-kedQt2UmnE
 * @autho Andre Violentyev
 */
var knapsack = function(V, W, wMax) {
    var T = [], // table that stores best solution for the given max capacity
        keep = [] // this table remembers if a particular object is put into the knapsack
    var i, w, itemValue, itemWeight, newBest

	// create DP tables
    for (i = 0; i <= V.length; i++) {
        T.push([])
        keep.push([])
    }

	// initialize the DP table
    for (w = 0; w <= wMax; w++) {
        T[0][w] = 0;
    }

    for (i = 1; i <= V.length; i++) {
        for (w = 0; w <= wMax; w++) {
            itemValue = V[i - 1]
            itemWeight = W[i - 1]
            newBest = itemValue + T[i - 1][w - itemWeight]

            if (W[i - 1] <= w && T[i - 1][w] < newBest) {
                T[i][w] = newBest
                keep[i][w] = 1 // mark that a new object is put into the knapsack
            } else {
                T[i][w] = T[i - 1][w] // use the solution from prevous row
                keep[i][w] = 0
            }
        }
    }

    console.log('T=', T)
    console.log('keep=', keep)

	// retrieve the path by walking up from bottom right corner
    w = wMax
    for (i = V.length; i > 0; i--) {
        console.log('i=', i, 'w=', w, 'keep[i][w]=', keep[i][w])
        if (keep[i][w] === 1) {
            w = w - W[i - 1]
            console.log('keep=', i, 'w=', w)
        }
    }

    return T[V.length][wMax]
}

var V = [10, 40, 30, 60] // values of the objects
var W = [5, 4, 6, 3] // weights of the objects
var wMax = 10; // maximum knapsack weight capacity

console.log('max value =', knapsack(V, W, wMax))