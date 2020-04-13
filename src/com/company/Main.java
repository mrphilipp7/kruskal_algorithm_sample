package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {


        int V = 9;  // Number of vertices in graph
        int E = 14;  // Number of edges in graph
        Graph graph = new Graph(V, E);

        // 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = 4;
        // 0-7
        graph.edge[1].src = 0;
        graph.edge[1].dest = 7;
        graph.edge[1].weight = 8;
        // 1-2
        graph.edge[2].src = 1;
        graph.edge[2].dest = 2;
        graph.edge[2].weight = 81;
        // 1-7
        graph.edge[3].src = 1;
        graph.edge[3].dest = 7;
        graph.edge[3].weight = 11;
        // 2-8
        graph.edge[4].src = 2;
        graph.edge[4].dest = 8;
        graph.edge[4].weight = 2;
        // 2-3
        graph.edge[5].src = 2;
        graph.edge[5].dest = 3;
        graph.edge[5].weight = 7;
        // 2-5
        graph.edge[6].src = 2;
        graph.edge[6].dest = 5;
        graph.edge[6].weight = 4;
        // 3-4
        graph.edge[7].src = 3;
        graph.edge[7].dest = 4;
        graph.edge[7].weight = 9;
        // 3-5
        graph.edge[8].src = 3;
        graph.edge[8].dest = 5;
        graph.edge[8].weight = 14;
        // 4-5
        graph.edge[9].src = 4;
        graph.edge[9].dest = 5;
        graph.edge[9].weight = 10;
        // 5-6
        graph.edge[10].src = 5;
        graph.edge[10].dest = 6;
        graph.edge[10].weight = 2;
        // 6-7
        graph.edge[11].src = 6;
        graph.edge[11].dest = 7;
        graph.edge[11].weight = 1;
        // 6-8
        graph.edge[12].src = 6;
        graph.edge[12].dest = 8;
        graph.edge[12].weight = 6;
        // 7-8
        graph.edge[13].src = 7;
        graph.edge[13].dest = 8;
        graph.edge[13].weight = 7;

        graph.KruskalMST();
    }

    static class Graph {

        class Edge implements Comparable<Edge> {
            int src, dest, weight;

            // comparator function used for sorting edges based on their weight
            public int compareTo(Edge compareEdge) {
                return this.weight - compareEdge.weight;
            }
        };

        // class to represent a subset for union-find
        class subset {
            int parent, rank;
        };

        int V, E;       // V -> # of vertices, E -> # of edges
        Edge edge[];    // collection of all edges

        // creates a graph with V vertices and E edges
        Graph(int v, int e) {
            V = v;
            E = e;
            edge = new Edge[E];
            for (int i = 0; i < e; ++i)
                edge[i] = new Edge();
        }

        // a utility function to find set of an element i (uses path compression technique)
        int find(subset subsets[], int i) {
            // find root and make root as parent of i (path compression)
            if (subsets[i].parent != i)
                subsets[i].parent = find(subsets, subsets[i].parent);

            return subsets[i].parent;
        }

        // function that does union of two sets of x and y (uses union by rank)
        void Union(subset subsets[], int x, int y) {
            int xroot = find(subsets, x);
            int yroot = find(subsets, y);

            // attach smaller rank tree under root of high rank tree (Union by Rank)
            if (subsets[xroot].rank < subsets[yroot].rank)
                subsets[xroot].parent = yroot;
            else if (subsets[xroot].rank > subsets[yroot].rank)
                subsets[yroot].parent = xroot;

                // if ranks are same, then make one as root and increment its rank by one
            else {
                subsets[yroot].parent = xroot;
                subsets[xroot].rank++;
            }
        }

        // main function to construct MST using Kruskal's algorithm
        void KruskalMST() {
            Edge result[] = new Edge[V];    // store the resultant MST
            int e = 0;              // index variable, used for result[]
            int i = 0;              // index variable, used for sorted edges
            for (i = 0; i < V; ++i)
                result[i] = new Edge();

            // step 1:  sort all the edges in non-decreasing order of their weight.
            // if we are not allowed to change the given graph, we
            // can create a copy of array of edges
            Arrays.sort(edge);

            // allocate memory for creating V ssubsets
            subset subsets[] = new subset[V];
            for (i = 0; i < V; ++i)
                subsets[i] = new subset();

            // create V subsets with single elements
            for (int v = 0; v < V; ++v) {
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }

            i = 0;  // index used to pick next edge

            // number of edges to be taken is equal to V-1
            while (e < V - 1) {
                // step 2: pick the smallest edge,
                // increment the index for next iteration
                Edge next_edge = new Edge();
                next_edge = edge[i++];

                int x = find(subsets, next_edge.src);
                int y = find(subsets, next_edge.dest);

                // if including this edge does't cause cycle,
                // include it in result and increment the index of result for next edge
                if (x != y) {
                    result[e++] = next_edge;
                    Union(subsets, x, y);
                }
                // else discard the next_edge
            }

            // print the contents of result[] to display the built MST
            System.out.println("Following are the edges in " +
                    "the constructed MST");
            for (i = 0; i < e; ++i)
                System.out.println(result[i].src + " -- " +
                        result[i].dest + " == " + result[i].weight);
        }
    }
}
