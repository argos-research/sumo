function speedWithoutTraCI = getSpeedWithoutTraCI(vehID)
%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getSpeedWithoutTraCI.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
speedWithoutTraCI = traci.vehicle.getUniversal(constants.VAR_SPEED_WITHOUT_TRACI, vehID);