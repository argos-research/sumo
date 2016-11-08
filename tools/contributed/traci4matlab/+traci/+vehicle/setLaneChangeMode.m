function setLaneChangeMode(vehID, lcm)
%setLaneChangeMode Sets the vehicle's lane change mode as a bitset.
% setLaneChangeMode(VEHID, LCM)Sets the vehicle's lane change mode as a 
%   bitset. For further details, see 'lane change mode' in 
%   http://sumo.dlr.de/wiki/TraCI/Change_Vehicle_State

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendIntCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_LANECHANGE_MODE, vehID, lcm);





