function edges = getEdges(routeID)
%getEdges Return the edges of the route.
%   edges = getEdges(ROUTEID) Returns a list of all edges in the route. The
%   list of edges is a cell array of strings whose elements are the IDs of
%   the edges.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getEdges.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
edges = traci.route.getUniversal(constants.VAR_EDGES, routeID);