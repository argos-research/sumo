function traveltime = getTraveltime(edgeID)
%getTraveltime Get estimated travel time in the edge.
%   traveltime = getTraveltime(EDGEID) Returns the estimated travel time in
%   seconds for the last time step on the given edge.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getTraveltime.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traveltime = traci.edge.getUniversal(constants.VAR_CURRENT_TRAVELTIME, edgeID);