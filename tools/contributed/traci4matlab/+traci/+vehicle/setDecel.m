function setDecel(vehID, decel)
%setDecel ets the maximum deceleration for this vehicle.
%   setDecel(VEHID,DECEL) Sets the maximum deceleration in m/s^2 for this 
%   vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_DECEL, vehID, decel);
