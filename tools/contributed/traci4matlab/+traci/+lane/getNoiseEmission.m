function noiseEmission = getNoiseEmission(laneID)
%getNoiseEmission Get the noise emission in the lane.
%   noiseEmission = getNoiseEmission(LANEID) Returns the noise emission in 
%   db for the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
noiseEmission = traci.lane.getUniversal(constants.VAR_NOISEEMISSION, laneID);