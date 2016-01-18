function lastStepMeanSpeed = getLastStepMeanSpeed(edgeID)
%getLastStepMeanSpeed Get the average speed on the edge.
%   lastStepMeanSpeed = getLastStepMeanSpeed(EDGEID) Returns the average 
%   speed in m/s for the last time step on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepMeanSpeed.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
lastStepMeanSpeed = traci.edge.getUniversal(constants.LAST_STEP_MEAN_SPEED, edgeID);