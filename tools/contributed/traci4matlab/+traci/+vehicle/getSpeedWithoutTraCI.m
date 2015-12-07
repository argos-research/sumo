function speedWithoutTraCI = getSpeedWithoutTraCI(vehID)
%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
speedWithoutTraCI = traci.vehicle.getUniversal(constants.VAR_SPEED_WITHOUT_TRACI, vehID);