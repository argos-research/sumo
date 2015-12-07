function setMaxSpeed(edgeID, speed)
%setMaxSpeed Set the maximum speed in the edge.
%   setMaxSpeed(EDGEID,SPEED) Set a new maximum speed (in m/s) for all 
%   lanes of the edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_EDGE_VARIABLE, constants.VAR_MAXSPEED, edgeID, speed)