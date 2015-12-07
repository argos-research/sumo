function NOxEmission = getNOxEmission(laneID)
%getNOxEmission Get the NOx emission in the lane.
%   NOxEmission = getNOxEmission(LANEID) Returns the NOx emission in mg for
%   the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
NOxEmission = traci.lane.getUniversal(constants.VAR_NOXEMISSION, laneID);