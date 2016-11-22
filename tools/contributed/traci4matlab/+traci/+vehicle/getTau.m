function tau = getTau(vehID)
%getTau Returns the driver's reaction time in s for this vehicle.
%   tau = getTau(VEHID) Returns the driver's reaction time in s for this 
%   vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getTau.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
tau = traci.vehicle.getUniversal(constants.VAR_TAU, vehID);