function traveltime = getTraveltime(laneID)
%getTraveltime Get estimated travel time in the lane.
%   traveltime = getTraveltime(LANEID) Returns the estimated travel time in
%   seconds for the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getTraveltime.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
traveltime = traci.lane.getUniversal(constants.VAR_CURRENT_TRAVELTIME, laneID);